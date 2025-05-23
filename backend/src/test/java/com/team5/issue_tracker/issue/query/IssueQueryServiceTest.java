package com.team5.issue_tracker.issue.query;

import com.team5.issue_tracker.issue.dto.IssueQueryDto;
import com.team5.issue_tracker.issue.dto.response.IssuePageResponse;
import com.team5.issue_tracker.issue.dto.response.IssueSummaryResponse;
import com.team5.issue_tracker.label.dto.response.LabelSummaryResponse;
import com.team5.issue_tracker.label.query.LabelQueryRepository;
import com.team5.issue_tracker.milestone.dto.response.MilestoneSummaryResponse;
import com.team5.issue_tracker.milestone.query.MilestoneQueryRepository;
import com.team5.issue_tracker.user.query.UserQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.team5.issue_tracker.user.dto.UserSummaryResponse;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IssueQueryServiceTest {

  @InjectMocks
  private IssueQueryService issueQueryService;

  @Mock
  private IssueQueryRepository issueQueryRepository;

  @Mock
  private LabelQueryRepository labelQueryRepository;

  @Mock
  private MilestoneQueryRepository milestoneQueryRepository;

  @Mock
  private UserQueryRepository userQueryRepository;

  @Test
  @DisplayName("전체 이슈 페이지를 IssuePageResponse의 형태로 가져올 수 있다.")
  void getIssuePage_success_정상_요청() {
    // given
    IssueQueryDto issueQueryDto1 = new IssueQueryDto(1L, "제목1", true, null, null);
    IssueQueryDto issueQueryDto2 = new IssueQueryDto(2L, "제목2", true, null, null);
    UserSummaryResponse userSummaryResponse1 = new UserSummaryResponse(1L, "작성자1", "이미지");
    UserSummaryResponse userSummaryResponse2 = new UserSummaryResponse(2L, "작성자2", "이미지");
    LabelSummaryResponse
        labelSummaryResponse1 = new LabelSummaryResponse(1L, "라벨1", "#007AFF", "#007AFF");
    LabelSummaryResponse
        labelSummaryResponse2 = new LabelSummaryResponse(2L, "라벨2", "#007AFF", "#007AFF");
    MilestoneSummaryResponse milestoneSummaryResponse1 = new MilestoneSummaryResponse(1L, "마일스톤1");
    MilestoneSummaryResponse milestoneSummaryResponse2 = new MilestoneSummaryResponse(2L, "마일스톤2");

    List<Long> issueIds = List.of(1L, 2L);
    Map<Long, List<LabelSummaryResponse>> labelMap = Map.of(
        1L, List.of(labelSummaryResponse1),
        2L, List.of(labelSummaryResponse2)
    );
    Map<Long, UserSummaryResponse> userMap = Map.of(
        1L, userSummaryResponse1,
        2L, userSummaryResponse2
    );
    Map<Long, MilestoneSummaryResponse> milestoneMap = Map.of(
        1L, milestoneSummaryResponse1,
        2L, milestoneSummaryResponse2
    );

    when(issueQueryRepository.findAllIssues()).thenReturn(List.of(issueQueryDto1, issueQueryDto2));
    when(userQueryRepository.getAuthorsByIssueIds(issueIds)).thenReturn(userMap);
    when(labelQueryRepository.getLabelListByIssueIds(issueIds)).thenReturn(labelMap);
    when(milestoneQueryRepository.getMilestonesByIds(issueIds)).thenReturn(milestoneMap);

    // when
    IssuePageResponse issuePageResponse = issueQueryService.getIssuePage();

    // then
    assertThat(issuePageResponse.getTotal()).isEqualTo(2);
    assertThat(issuePageResponse.getIssues()).hasSize(2);

    List<IssueSummaryResponse> issueSummaryResponses = issuePageResponse.getIssues();
    assertThat(issueSummaryResponses.get(0).getId()).isEqualTo(1L);
    assertThat(issueSummaryResponses.get(0).getAuthor()).isEqualTo(userSummaryResponse1);
    assertThat(issueSummaryResponses.get(0).getLabels()).containsExactly(labelSummaryResponse1);
    assertThat(issueSummaryResponses.get(0).getMilestone()).isEqualTo(milestoneSummaryResponse1);

    assertThat(issueSummaryResponses.get(1).getId()).isEqualTo(2L);
    assertThat(issueSummaryResponses.get(1).getAuthor()).isEqualTo(userSummaryResponse2);
    assertThat(issueSummaryResponses.get(1).getLabels()).containsExactly(labelSummaryResponse2);
    assertThat(issueSummaryResponses.get(1).getMilestone()).isEqualTo(milestoneSummaryResponse2);
  }
}
