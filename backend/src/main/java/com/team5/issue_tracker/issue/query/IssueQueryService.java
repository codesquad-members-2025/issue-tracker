package com.team5.issue_tracker.issue.query;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.team5.issue_tracker.common.comment.query.CommentQueryRepository;
import com.team5.issue_tracker.issue.dto.IssueQueryDto;
import com.team5.issue_tracker.issue.dto.IssueSearchCondition;
import com.team5.issue_tracker.issue.dto.request.IssueSearchRequest;
import com.team5.issue_tracker.issue.dto.response.IssueBaseResponse;
import com.team5.issue_tracker.common.comment.dto.CommentResponse;
import com.team5.issue_tracker.issue.dto.response.IssueDetailResponse;
import com.team5.issue_tracker.issue.dto.response.IssuePageResponse;
import com.team5.issue_tracker.issue.dto.response.IssueSummaryResponse;
import com.team5.issue_tracker.issue.mapper.IssueMapper;
import com.team5.issue_tracker.label.dto.response.LabelResponse;
import com.team5.issue_tracker.label.dto.response.LabelSummaryResponse;
import com.team5.issue_tracker.label.query.LabelQueryRepository;
import com.team5.issue_tracker.milestone.dto.response.MilestoneResponse;
import com.team5.issue_tracker.milestone.dto.response.MilestoneSummaryResponse;
import com.team5.issue_tracker.milestone.query.MilestoneQueryRepository;
import com.team5.issue_tracker.user.dto.UserPageResponse;
import com.team5.issue_tracker.user.dto.UserSummaryResponse;
import com.team5.issue_tracker.user.query.UserQueryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class IssueQueryService {
  private final IssueQueryRepository issueQueryRepository;
  private final LabelQueryRepository labelQueryRepository;
  private final MilestoneQueryRepository milestoneQueryRepository;
  private final UserQueryRepository userQueryRepository;
  private final CommentQueryRepository commentQueryRepository;

  @Transactional(readOnly = true)
  public IssuePageResponse getIssuePage(IssueSearchRequest searchRequest) {
    log.debug("조건에 맞는 이슈 조회 요청");
    IssueSearchCondition searchCondition = getCondition(searchRequest);
    List<IssueQueryDto> issueQueryDtos =
        issueQueryRepository.findIssuesByCondition(searchCondition);

    List<Long> issueIds = issueQueryDtos.stream()
        .map(IssueQueryDto::getId)
        .toList();

    Map<Long, List<LabelSummaryResponse>> labelMap =
        labelQueryRepository.getLabelListByIssueIds(issueIds);
    Map<Long, MilestoneSummaryResponse> milestoneMap =
        milestoneQueryRepository.getMilestonesByIds(issueIds);
    Map<Long, UserSummaryResponse> authorMap = userQueryRepository.getAuthorsByIssueIds(issueIds);

    List<IssueSummaryResponse> issueSummaryResponseList =
        IssueMapper.toSummaryResponse(issueQueryDtos,
            labelMap, authorMap, milestoneMap);

    return new IssuePageResponse((long) issueSummaryResponseList.size(), 0L, //TODO: 페이지 기능
        (long) issueSummaryResponseList.size(), searchRequest.toQueryString(),
        issueSummaryResponseList);
  }

  public UserPageResponse getIssueAuthors() {
    log.debug("전체 작성자 조회 요청");
    List<UserSummaryResponse> authorSummaries = issueQueryRepository.findDistinctAuthors();
    return new UserPageResponse((long) authorSummaries.size(), 0L, (long) authorSummaries.size(),
        authorSummaries);
  }

  private IssueSearchCondition getCondition(IssueSearchRequest searchRequest) {
    Long assigneeId =
        userQueryRepository.getUserIdByUsername(searchRequest.getAssigneeName()).orElse(null);
    List<Long> labelIds = labelQueryRepository.getLabelIdsByNames(searchRequest.getLabelNames());
    Long milestoneId =
        milestoneQueryRepository.getMilestoneIdByName(searchRequest.getMilestoneName())
            .orElse(null);
    Long authorId =
        userQueryRepository.getUserIdByUsername(searchRequest.getAuthorName()).orElse(null);
    log.info("assigneeId: {}, labelIds: {}, milestoneId: {}, authorId: {}",
        assigneeId, labelIds, milestoneId, authorId);

    return new IssueSearchCondition(
        searchRequest.getIsOpen(),
        assigneeId,
        labelIds,
        milestoneId,
        authorId
    );
  }

  @Transactional(readOnly = true)
  public IssueDetailResponse getIssueById(Long issueId) {
    IssueBaseResponse issueBase = issueQueryRepository.findIssueDetailById(issueId);
    List<LabelResponse> labelList = labelQueryRepository.getLabelsByIssueId(issueId);
    UserSummaryResponse author = userQueryRepository.getAuthorById(issueId);
    List<UserSummaryResponse> assignees = userQueryRepository.getAssigneesByIssueId(issueId);
    MilestoneResponse milestone = milestoneQueryRepository.getMilestoneByIssueId(issueId);
    List<CommentResponse> comments = commentQueryRepository.getCommentsByIssueId(issueId);

    return IssueMapper.toDetailResponse(issueBase, labelList, author, assignees, milestone,
        comments);
  }
}
