package elbin_bank.issue_tracker.issue.application.query.mapper;

import elbin_bank.issue_tracker.issue.application.query.dto.IssueDetailResponseDto;
import elbin_bank.issue_tracker.issue.application.query.dto.IssuesResponseDto;
import elbin_bank.issue_tracker.issue.application.query.dto.MilestoneDto;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueCountProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueDetailProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.UserInfoProjection;
import elbin_bank.issue_tracker.label.infrastructure.query.projection.LabelProjection;
import elbin_bank.issue_tracker.milestone.application.ProgressRateCalculator;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class IssueDtoMapper {

    public IssuesResponseDto toIssuesResponseDto(List<IssueProjection> issueProjections,
                                                 IssueCountProjection issueCountProjection) {
        return new IssuesResponseDto(issueProjections,
                issueCountProjection.openCount(),
                issueCountProjection.closedCount());
    }

    public IssueDetailResponseDto toIssueDetailDto(IssueDetailProjection issueDetailProjection,
                                                   List<LabelProjection> labelProjections,
                                                   List<UserInfoProjection> userInfoProjections) {
        MilestoneDto milestone = Optional.ofNullable(issueDetailProjection.milestoneId())
                .map(id -> new MilestoneDto(
                        id,
                        issueDetailProjection.milestoneName(),
                        ProgressRateCalculator.calculate(
                                issueDetailProjection.milestoneTotalIssues(),
                                issueDetailProjection.milestoneClosedIssues()
                        )
                ))
                .orElse(null);

        return new IssueDetailResponseDto(
                issueDetailProjection.id(),
                new UserInfoProjection(issueDetailProjection.authorId(),
                        issueDetailProjection.authorNickname(),
                        issueDetailProjection.authorProfileImage()),
                issueDetailProjection.title(),
                issueDetailProjection.contents(),
                labelProjections,
                milestone,
                userInfoProjections,
                issueDetailProjection.isClosed(),
                issueDetailProjection.createdAt(),
                issueDetailProjection.updatedAt()
        );
    }

}
