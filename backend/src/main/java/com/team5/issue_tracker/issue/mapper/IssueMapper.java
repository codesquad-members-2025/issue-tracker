package com.team5.issue_tracker.issue.mapper;

import java.util.List;
import java.util.Map;

import com.team5.issue_tracker.common.comment.dto.CommentResponse;
import com.team5.issue_tracker.issue.dto.IssueQueryDto;
import com.team5.issue_tracker.issue.dto.response.IssueBaseResponse;
import com.team5.issue_tracker.issue.dto.response.IssueDetailResponse;
import com.team5.issue_tracker.issue.dto.response.IssueSummaryResponse;
import com.team5.issue_tracker.label.dto.response.LabelResponse;
import com.team5.issue_tracker.label.dto.response.LabelSummaryResponse;
import com.team5.issue_tracker.milestone.dto.response.MilestoneResponse;
import com.team5.issue_tracker.milestone.dto.response.MilestoneSummaryResponse;
import com.team5.issue_tracker.user.dto.UserSummaryResponse;

public class IssueMapper {
  public static List<IssueSummaryResponse> toSummaryResponse(
      List<IssueQueryDto> issueQueryDtos,
      Map<Long, List<LabelSummaryResponse>> labels,
      Map<Long, UserSummaryResponse> userDto,
      Map<Long, MilestoneSummaryResponse> milestoneDto
  ) {
    return issueQueryDtos.stream()
        .map(issueQueryDto -> {
          Long issueId = issueQueryDto.getId();
          return new IssueSummaryResponse(
              issueId,
              issueQueryDto.getTitle(),
              issueQueryDto.getIsOpen(),
              labels.getOrDefault(issueId, List.of()),
              userDto.get(issueId),
              milestoneDto.get(issueId),
              issueQueryDto.getCreatedAt(),
              issueQueryDto.getUpdatedAt(),
              0L // TODO: 댓글 수는 추후에 구현
          );
        })
        .toList();
  }

  public static IssueDetailResponse toDetailResponse(
      IssueBaseResponse issue,
      List<LabelResponse> labels,
      UserSummaryResponse author,
      List<UserSummaryResponse> assignees,
      MilestoneResponse milestone,
      List<CommentResponse> comments
  ) {
    return new IssueDetailResponse(
        issue.getId(),
        issue.getTitle(),
        issue.getBody(),
        issue.getIsOpen(),
        labels,
        author,
        assignees,
        milestone,
        issue.getCreatedAt(),
        issue.getUpdatedAt(),
        comments
    );
  }
}
