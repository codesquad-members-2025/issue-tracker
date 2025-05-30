package com.team5.issue_tracker.issue.query;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.team5.issue_tracker.comment.query.CommentQueryRepository;
import com.team5.issue_tracker.issue.dto.IssueQueryDto;
import com.team5.issue_tracker.issue.dto.IssueSearchCondition;
import com.team5.issue_tracker.issue.dto.request.IssueSearchRequest;
import com.team5.issue_tracker.issue.dto.response.IssueBaseResponse;
import com.team5.issue_tracker.comment.dto.CommentResponse;
import com.team5.issue_tracker.issue.dto.response.IssueCountResponse;
import com.team5.issue_tracker.issue.dto.response.IssueDetailResponse;
import com.team5.issue_tracker.issue.dto.response.IssuePageResponse;
import com.team5.issue_tracker.issue.dto.response.IssueSummaryResponse;
import com.team5.issue_tracker.issue.dto.response.UserPreviewResponse;
import com.team5.issue_tracker.issue.mapper.IssueMapper;
import com.team5.issue_tracker.label.dto.response.LabelResponse;
import com.team5.issue_tracker.label.query.LabelQueryRepository;
import com.team5.issue_tracker.milestone.dto.response.MilestoneResponse;
import com.team5.issue_tracker.milestone.dto.response.MilestoneSummaryResponse;
import com.team5.issue_tracker.milestone.query.MilestoneQueryRepository;
import com.team5.issue_tracker.user.dto.UserScrollResponse;
import com.team5.issue_tracker.user.dto.UserSummaryResponse;
import com.team5.issue_tracker.user.query.UserQueryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssueQueryService {
  private final IssueQueryRepository issueQueryRepository;
  private final LabelQueryRepository labelQueryRepository;
  private final MilestoneQueryRepository milestoneQueryRepository;
  private final UserQueryRepository userQueryRepository;
  private final CommentQueryRepository commentQueryRepository;

  public IssuePageResponse getPagedIssues(IssueSearchRequest searchRequest, Integer page,
      Integer perPage) {
    log.debug("조건에 맞는 이슈 조회 요청");
    IssueSearchCondition searchCondition = getCondition(searchRequest);
    List<IssueQueryDto> issueQueryDtos =
        issueQueryRepository.findIssuesByCondition(searchCondition, page, perPage);
    IssueCountResponse issueCountResponse =
        issueQueryRepository.getIssueCountByCondition(searchCondition);

    List<Long> issueIds = issueQueryDtos.stream()
        .map(IssueQueryDto::getId)
        .toList();

    Map<Long, List<LabelResponse>> labelMap =
        labelQueryRepository.getLabelListByIssueIds(issueIds);
    Map<Long, MilestoneSummaryResponse> milestoneMap =
        milestoneQueryRepository.getMilestonesByIds(issueIds);
    Map<Long, UserPreviewResponse> authorMap = userQueryRepository.getAuthorsByIssueIds(issueIds);
    Map<Long, List<UserSummaryResponse>> assigneeMap =
        userQueryRepository.getAssigneesByIssueIds(issueIds);

    List<IssueSummaryResponse> issueSummaryResponseList =
        IssueMapper.toSummaryResponse(issueQueryDtos,
            labelMap, authorMap, milestoneMap, assigneeMap);

    return new IssuePageResponse(issueSummaryResponseList.size(), page, perPage,
        searchRequest.toQueryString(), issueCountResponse.getOpenCount(),
        issueCountResponse.getClosedCount(), issueSummaryResponseList);
  }

  public UserScrollResponse getScrolledIssueAuthors(String cursor, Integer limit) {
    log.debug("전체 작성자 스크롤 조회 요청");
    List<UserSummaryResponse> usersPlusOne =
        issueQueryRepository.findDistinctAuthors(cursor, limit);

    Boolean hasNext = usersPlusOne.size() > limit;
    List<UserSummaryResponse> users = hasNext ? usersPlusOne.subList(0, limit) : usersPlusOne;
    String nextCursor = hasNext ? users.getLast().getUsername() : null;

    return new UserScrollResponse(hasNext, nextCursor, users);
  }

  private IssueSearchCondition getCondition(IssueSearchRequest searchRequest) {
    Long assigneeId =
        userQueryRepository.getUserIdByUsername(searchRequest.getAssigneeName());
    List<Long> labelIds = labelQueryRepository.getLabelIdsByNames(searchRequest.getLabelNames());
    Long milestoneId =
        milestoneQueryRepository.getMilestoneIdByName(searchRequest.getMilestoneName());
    Long authorId =
        userQueryRepository.getUserIdByUsername(searchRequest.getAuthorName());
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

  public IssueDetailResponse getIssueById(Long issueId) {
    IssueBaseResponse issueBase = issueQueryRepository.findIssueDetailById(issueId);
    List<LabelResponse> labelList = labelQueryRepository.getLabelsByIssueId(issueId);
    UserSummaryResponse author = userQueryRepository.getAuthorById(issueBase.getAuthorId());
    List<UserSummaryResponse> assignees = userQueryRepository.getAssigneesByIssueId(issueId);
    MilestoneResponse milestone =
        milestoneQueryRepository.getMilestoneById(issueBase.getMilestoneId());
    List<CommentResponse> comments = commentQueryRepository.getCommentsByIssueId(issueId);
    return IssueMapper.toDetailResponse(issueBase, labelList, author, assignees, milestone,
        comments);
  }
}
