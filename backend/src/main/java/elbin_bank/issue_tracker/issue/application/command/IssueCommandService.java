package elbin_bank.issue_tracker.issue.application.command;

import elbin_bank.issue_tracker.issue.application.command.dto.IssueCreateResponseDto;
import elbin_bank.issue_tracker.issue.domain.Issue;
import elbin_bank.issue_tracker.issue.domain.IssueCommandRepository;
import elbin_bank.issue_tracker.issue.exception.IssueDetailNotFoundException;
import elbin_bank.issue_tracker.issue.presentation.command.dto.request.IssueCreateRequestDto;
import elbin_bank.issue_tracker.issue.presentation.command.dto.request.IssueStateUpdateRequestDto;
import elbin_bank.issue_tracker.label.domain.LabelCommandRepository;
import elbin_bank.issue_tracker.user.domain.UserCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IssueCommandService {

    private final IssueCommandRepository issueCommandRepository;
    private final LabelCommandRepository labelCommandRepository;
    private final UserCommandRepository userCommandRepository;

    @Transactional
    public IssueCreateResponseDto createIssue(IssueCreateRequestDto requestDto) {
        Issue issue = Issue.of(
                1L, // todo jwt에서 꺼내와야댐
                requestDto.title(),
                requestDto.content(),
                requestDto.milestone(),
                false
        );
        Issue newIssue = issueCommandRepository.save(issue);
        labelCommandRepository.saveLabelsToIssue(newIssue.getId(), requestDto.labels());
        userCommandRepository.saveAssigneesToIssue(newIssue.getId(), requestDto.assignees());

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
    }

}
