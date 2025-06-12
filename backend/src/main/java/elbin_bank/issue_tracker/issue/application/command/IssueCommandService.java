package elbin_bank.issue_tracker.issue.application.command;

import elbin_bank.issue_tracker.common.exception.ForbiddenException;
import elbin_bank.issue_tracker.issue.application.command.dto.IssueCreateResponseDto;
import elbin_bank.issue_tracker.issue.application.command.event.IssueMilestoneChangedEvent;
import elbin_bank.issue_tracker.issue.application.command.event.IssueMilestoneCreateEvent;
import elbin_bank.issue_tracker.issue.application.command.event.IssueMilestoneDeleteEvent;
import elbin_bank.issue_tracker.issue.application.command.event.IssueMilestoneStateEvent;
import elbin_bank.issue_tracker.issue.application.query.repository.IssueQueryRepository;
import elbin_bank.issue_tracker.issue.domain.Issue;
import elbin_bank.issue_tracker.issue.domain.IssueCommandRepository;
import elbin_bank.issue_tracker.issue.exception.IssueDetailNotFoundException;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.UserInfoProjection;
import elbin_bank.issue_tracker.issue.presentation.command.dto.request.*;
import elbin_bank.issue_tracker.label.application.query.repository.LabelQueryRepository;
import elbin_bank.issue_tracker.label.domain.LabelCommandRepository;
import elbin_bank.issue_tracker.user.domain.UserCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueCommandService {

    private final IssueCommandRepository issueCommandRepository;
    private final IssueQueryRepository issueQueryRepository;
    private final LabelQueryRepository labelQueryRepository;
    private final LabelCommandRepository labelCommandRepository;
    private final UserCommandRepository userCommandRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public IssueCreateResponseDto createIssue(IssueCreateRequestDto requestDto, Long userId) {
        Issue issue = Issue.of(
                userId,
                requestDto.title(),
                requestDto.content(),
                requestDto.milestone(),
                false
        );
        Issue newIssue = issueCommandRepository.save(issue);
        labelCommandRepository.saveLabelsToIssue(newIssue.getId(), requestDto.labels());
        userCommandRepository.saveAssigneesToIssue(newIssue.getId(), requestDto.assignees());

        applicationEventPublisher.publishEvent(new IssueMilestoneCreateEvent(
                newIssue.getMilestoneId()
        ));

        return new IssueCreateResponseDto(newIssue.getId());
    }

    @Transactional
    public void updateState(long issueId, IssueStateUpdateRequestDto dto) {
        Issue issue = issueCommandRepository.findById(issueId)
                .orElseThrow(() -> new IssueDetailNotFoundException(issueId));

        boolean wasClosed = issue.isClosed();
        boolean targetClosed = dto.targetClosed();

        issue.changeState(targetClosed);
        if (issue.isClosed() == wasClosed) {
            return;
        }

        issueCommandRepository.updateState(issueId, targetClosed);

        applicationEventPublisher.publishEvent(new IssueMilestoneStateEvent(
                issue.getMilestoneId(),
                targetClosed
        ));
    }

    @Transactional
    public void updateTitle(long issueId, IssueTitleUpdateRequestDto dto) {
        Issue issue = issueCommandRepository.findById(issueId)
                .orElseThrow(() -> new IssueDetailNotFoundException(issueId));

        if (issue.getTitle().equals(dto.title())) {
            return;
        }

        issue.setTitle(dto.title());
        issueCommandRepository.save(issue);
    }

    @Transactional
    public void updateContent(long issueId, IssueContentUpdateRequestDto dto) {
        Issue issue = issueCommandRepository.findById(issueId)
                .orElseThrow(() -> new IssueDetailNotFoundException(issueId));

        if (issue.getContents().equals(dto.content())) {
            return;
        }

        issue.setContents(dto.content());
        issueCommandRepository.save(issue);
    }

    @Transactional
    public void updateMilestone(long issueId, IssueMilestoneUpdateRequestDto dto) {
        Issue issue = issueCommandRepository.findById(issueId)
                .orElseThrow(() -> new IssueDetailNotFoundException(issueId));

        Long beforeMilestoneId = issue.getMilestoneId();
        if (beforeMilestoneId != null && beforeMilestoneId.equals(dto.id())) {
            return;
        }

        issue.setMilestoneId(dto.id());
        issueCommandRepository.save(issue);

        if (dto.id() == null) {
            applicationEventPublisher.publishEvent(new IssueMilestoneDeleteEvent(
                    beforeMilestoneId,
                    issue.isClosed()
            ));
            return;
        }

        applicationEventPublisher.publishEvent(
                new IssueMilestoneChangedEvent(
                        beforeMilestoneId,
                        issue.getMilestoneId(),
                        issue.isClosed()
                )
        );
    }

    @Transactional
    public void updateAssignees(long issueId, IssueAssigneesUpdateRequestDto dto) {
        List<Long> existingUserIds = issueQueryRepository.findAssigneeByIssueId(issueId).stream()
                .map(UserInfoProjection::id)
                .toList();

        List<Long> assignees = dto.assignees();
        if (assignees.equals(existingUserIds)) {
            return;
        }

        List<Long> toRemove = existingUserIds.stream()
                .filter(id -> !assignees.contains(id))
                .toList();

        if (!toRemove.isEmpty()) {
            userCommandRepository.deleteAssigneesFromIssue(issueId, toRemove);
        }

        userCommandRepository.saveAssigneesToIssue(issueId, assignees);
    }

    @Transactional
    public void updateLabels(long issueId, IssueLabelsUpdateRequestDto dto) {
        List<Long> existingLabels = labelQueryRepository.findLabelIdsByIssueId(issueId);

        List<Long> labels = dto.labels();
        if (labels.equals(existingLabels)) {
            return;
        }

        List<Long> toRemove = existingLabels.stream()
                .filter(id -> !labels.contains(id))
                .toList();

        if (!toRemove.isEmpty()) {
            labelCommandRepository.deleteLabelsFromIssue(issueId, toRemove);
        }

        labelCommandRepository.saveLabelsToIssue(issueId, labels);
    }

    @Transactional
    public void deleteIssue(long issueId, long userId) {
        Issue issue = issueCommandRepository.findById(issueId)
                .orElseThrow(() -> new IssueDetailNotFoundException(issueId));
        if (issue.getAuthorId() != userId) {
            throw new ForbiddenException("You do not have permission to delete this issue.");
        }

        issueCommandRepository.deleteById(issueId);

        applicationEventPublisher.publishEvent(new IssueMilestoneDeleteEvent(
                issue.getMilestoneId(),
                issue.isClosed()
        ));
    }

}
