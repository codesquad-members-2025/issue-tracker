package com.team5.issue_tracker.issue.service;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import com.team5.issue_tracker.common.dto.ApiResponse;
import com.team5.issue_tracker.issue.domain.Issue;
import com.team5.issue_tracker.issue.dto.IssueSummaryResponse;
import com.team5.issue_tracker.issue.dto.IssuePageResponse;
import com.team5.issue_tracker.issue.repository.IssueRepository;
import com.team5.issue_tracker.label.dto.LabelResponse;
import com.team5.issue_tracker.label.service.LabelService;
import com.team5.issue_tracker.milestone.dto.MilestoneResponse;
import com.team5.issue_tracker.milestone.service.MilestoneService;
import com.team5.issue_tracker.user.dto.UserSummaryResponse;
import com.team5.issue_tracker.user.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IssueServiceTest {
    @Mock
    private IssueRepository issueRepository;
    @Mock
    private UserService userService;
    @Mock
    private LabelService labelService;
    @Mock
    private MilestoneService milestoneService;
    private IssueService issueService;

  @BeforeEach
  void setUp() {
    issueService = new IssueService(issueRepository, userService, labelService, milestoneService); // 생성자 주입
  }

  @Test
  @DisplayName("모든 이슈 리스트를 반환한다.")
  void getIssueListPage_모든_이슈_반환() {
    // given
    Issue issue1 = new Issue("제목1", "본문", null, 1L, 1L, true);
    ReflectionTestUtils.setField(issue1, "id", 1L);
    ReflectionTestUtils.setField(issue1, "createdAt", LocalDateTime.now());
    Issue issue2 = new Issue("제목2", "본문", null, 1L, 1L, true);
    ReflectionTestUtils.setField(issue2, "id", 2L);
    ReflectionTestUtils.setField(issue2, "createdAt", LocalDateTime.now());

    UserSummaryResponse userSummaryResponse1 = new UserSummaryResponse(1L, "작성자1", "작성자 이메일");
    UserSummaryResponse userSummaryResponse2 = new UserSummaryResponse(2L, "작성자2", "작성자 이메일");
    LabelResponse labelResponse1 = new LabelResponse(1L, "라벨1", "라벨 설명");
    LabelResponse labelResponse2 = new LabelResponse(2L, "라벨2", "라벨 설명");
    MilestoneResponse milestoneResponse1 = new MilestoneResponse(1L, "마일스톤1");
    MilestoneResponse milestoneResponse2 = new MilestoneResponse(2L, "마일스톤2");

    when(issueRepository.findAll()).thenReturn(List.of(issue1, issue2));
    when(userService.getAuthorResponseById(1L)).thenReturn(userSummaryResponse1);
    when(labelService.getLabelResponsesByIssueId(1L)).thenReturn(List.of(labelResponse1));
    when(milestoneService.getMilestoneResponseById(1L)).thenReturn(milestoneResponse1);
    when(userService.getAuthorResponseById(2L)).thenReturn(userSummaryResponse2);
    when(labelService.getLabelResponsesByIssueId(2L)).thenReturn(List.of(labelResponse2));
    when(milestoneService.getMilestoneResponseById(2L)).thenReturn(milestoneResponse2);

    // when
    ApiResponse<IssuePageResponse> result = issueService.getIssueListPage();

    // then
    assertThat(result.isSuccess()).isTrue();
    assertThat(result.getData().getTotal()).isEqualTo(2);

    List<IssueSummaryResponse> issues = result.getData().getIssues();
    assertThat(issues).hasSize(2);
    assertThat(issues.get(0).getId()).isEqualTo(1L);
    assertThat(issues.get(0).getTitle()).isEqualTo("제목1");
    assertThat(issues.get(0).getAuthor()).isEqualTo(userSummaryResponse1);
    assertThat(issues.get(0).getLabels()).containsExactly(labelResponse1);
    assertThat(issues.get(0).getMilestone()).isEqualTo(milestoneResponse1);

    assertThat(issues.get(1).getId()).isEqualTo(2L);
    assertThat(issues.get(1).getTitle()).isEqualTo("제목2");
    assertThat(issues.get(1).getAuthor()).isEqualTo(userSummaryResponse2);
    assertThat(issues.get(1).getLabels()).containsExactly(labelResponse2);
    assertThat(issues.get(1).getMilestone()).isEqualTo(milestoneResponse2);
  }
}
