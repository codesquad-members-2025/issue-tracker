package com.team5.issue_tracker.issue.query;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.team5.issue_tracker.issue.dto.IssueQueryDto;
import com.team5.issue_tracker.issue.dto.response.IssuePageResponse;
import com.team5.issue_tracker.issue.dto.response.IssueSummaryResponse;
import com.team5.issue_tracker.issue.mapper.IssueMapper;
import com.team5.issue_tracker.label.dto.response.LabelSummaryResponse;
import com.team5.issue_tracker.label.query.LabelQueryRepository;
import com.team5.issue_tracker.milestone.dto.MilestoneResponse;
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

  public IssuePageResponse getIssuePage() {
    log.debug("전체 이슈 페이지 조회 요청");
    List<IssueQueryDto> issueQueryDtos = issueQueryRepository.findAllIssues();
    List<Long> issueIds = issueQueryDtos.stream()
        .map(IssueQueryDto::getId)
        .toList();

    Map<Long, List<LabelSummaryResponse>> labelMap = labelQueryRepository.getLabelListByIssueIds(issueIds);
    Map<Long, MilestoneResponse> milestoneMap =
        milestoneQueryRepository.getMilestonesByIds(issueIds);
    Map<Long, UserSummaryResponse> authorMap = userQueryRepository.getAuthorsByIssueIds(issueIds);

    List<IssueSummaryResponse> issueSummaryResponseList =
        IssueMapper.toSummaryResponse(issueQueryDtos,
            labelMap, authorMap, milestoneMap);

    return new IssuePageResponse((long) issueSummaryResponseList.size(), 0L, //TODO: 페이지 기능
        (long) issueSummaryResponseList.size(),
        issueSummaryResponseList);
  }

  public UserPageResponse getIssueAuthors() {
    log.debug("전체 작성자 조회 요청");
    List<UserSummaryResponse> authorSummaries = issueQueryRepository.findDistinctAuthors();
    return new UserPageResponse((long) authorSummaries.size(), 0L, (long) authorSummaries.size(),
        authorSummaries);
  }
}
