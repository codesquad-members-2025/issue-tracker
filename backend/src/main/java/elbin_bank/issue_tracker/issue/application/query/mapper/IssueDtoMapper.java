package elbin_bank.issue_tracker.issue.application.query.mapper;

import elbin_bank.issue_tracker.issue.application.query.dto.IssueDetailResponseDto;
import elbin_bank.issue_tracker.issue.application.query.dto.IssuesResponseDto;
import elbin_bank.issue_tracker.issue.application.query.dto.MilestoneDto;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.UserInfoProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueCountProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueDetailProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueProjection;
import elbin_bank.issue_tracker.label.infrastructure.query.projection.LabelProjection;
import elbin_bank.issue_tracker.milestone.application.ProgressRateCalculator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class IssueDtoMapper {

    public IssuesResponseDto toIssuesResponseDto(List<IssueProjection> issueProjections,
                                                 Map<Long, List<LabelProjection>> labelProjections,
                                                 Map<Long, List<String>> assigneeNames,
                                                 IssueCountProjection issueCountProjection) {
        return new IssuesResponseDto(issueProjections,
                issueCountProjection.openCount(),
                issueCountProjection.closedCount());
    }

    public IssueDetailResponseDto toIssueDetailDto(IssueDetailProjection issueDetailProjection,
                                                   List<LabelProjection> labelProjections,
                                                   List<UserInfoProjection> userInfoProjections) {
        int progressRate = ProgressRateCalculator.calculate(issueDetailProjection.milestoneTotalIssues(),
                issueDetailProjection.milestoneClosedIssues());

        return new IssueDetailResponseDto(
                issueDetailProjection.id(),
                new UserInfoProjection(issueDetailProjection.authorId(),
                        issueDetailProjection.authorNickname(),
                        issueDetailProjection.authorProfileImage()),
                issueDetailProjection.title(),
                issueDetailProjection.contents(),
                labelProjections,
                new MilestoneDto(issueDetailProjection.milestoneId(),
                        issueDetailProjection.milestoneName(),
                        progressRate),
                userInfoProjections,
                issueDetailProjection.isClosed(),
                issueDetailProjection.createdAt(),
                issueDetailProjection.updatedAt()
        );
    }

}
