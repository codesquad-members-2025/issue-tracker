package elbin_bank.issue_tracker.issue.application.query.mapper;

import elbin_bank.issue_tracker.issue.application.query.dto.IssueDetailResponseDto;
import elbin_bank.issue_tracker.issue.application.query.dto.IssueDto;
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
import java.util.Map;
import java.util.Optional;

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
