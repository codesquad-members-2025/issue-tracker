package elbin_bank.issue_tracker.issue.application.query.mapper;

import elbin_bank.issue_tracker.issue.application.query.dto.*;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueCountProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueDetailProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.UserInfoProjection;
import elbin_bank.issue_tracker.label.infrastructure.query.projection.LabelProjection;
import elbin_bank.issue_tracker.milestone.application.ProgressRateCalculator;
import elbin_bank.issue_tracker.milestone.infrastructure.query.projection.MilestoneProjection;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class IssueDtoMapper {

    public IssuesResponseDto toIssuesResponseDto(List<IssueProjection> issueProjections,
                                                 Map<Long, List<String>> assigneeNames,
                                                 Map<Long, List<LabelProjection>> labels,
                                                 IssueCountProjection issueCountProjection) {
        return new IssuesResponseDto(
                issueProjections.stream().map(i -> new IssueDto(
                        i.id(),
                        i.author(),
                        i.title(),
                        i.isClosed(),
                        labels.getOrDefault(i.id(), List.of()),
                        assigneeNames.getOrDefault(i.id(), List.of()),
                        i.createdAt(),
                        i.updatedAt(),
                        i.milestone()
                )).toList(),
                issueCountProjection.openCount(),
                issueCountProjection.closedCount());
    }

    public IssueDetailResponseDto toIssueDetailDto(IssueDetailProjection issueDetailProjection) {
        return new IssueDetailResponseDto(
                issueDetailProjection.id(),
                new UserInfoProjection(
                        issueDetailProjection.authorId(),
                        issueDetailProjection.authorNickname(),
                        issueDetailProjection.authorProfileImage()
                ),
                issueDetailProjection.title(),
                issueDetailProjection.contents(),
                issueDetailProjection.isClosed(),
                issueDetailProjection.createdAt(),
                issueDetailProjection.updatedAt()
        );
    }

    public LabelsResponseDto toLabelsResponseDto(List<LabelProjection> labelProjection) {
        return new LabelsResponseDto(
                labelProjection
        );
    }

    public AssigneesResponseDto toAssigneesResponseDto(List<UserInfoProjection> assignees) {
        return new AssigneesResponseDto(
                assignees.stream().map(a -> new UserInfoProjection(
                        a.id(),
                        a.nickname(),
                        a.profileImage()
                )).toList()
        );
    }

    public MilestoneResponseDto toMilestoneResponseDto(MilestoneProjection milestoneProjection) {
        int progressRate = ProgressRateCalculator.calculate(milestoneProjection.totalIssueCount(),
                milestoneProjection.closedIssueCount());

        return new MilestoneResponseDto(
                milestoneProjection.id(),
                milestoneProjection.title(),
                progressRate
        );
    }

}
