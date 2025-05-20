package com.team5.issue_tracker.issue.service;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import com.team5.issue_tracker.issue.domain.Issue;
import com.team5.issue_tracker.issue.domain.IssueAssignee;
import com.team5.issue_tracker.issue.domain.IssueLabel;
import com.team5.issue_tracker.issue.dto.request.IssueCreateRequest;
import com.team5.issue_tracker.issue.repository.IssueAssigneeRepository;
import com.team5.issue_tracker.issue.repository.IssueLabelRepository;
import com.team5.issue_tracker.issue.repository.IssueRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class IssueServiceTest {
  @Autowired
  private IssueService issueService;

  @Autowired
  private IssueRepository issueRepository;

  @Autowired
  private IssueLabelRepository issueLabelRepository;

  @Autowired
  private IssueAssigneeRepository issueAssigneeRepository;

  @Test
  @DisplayName("정상 요청 시 이슈와 라벨/담당자가 저장된다")
  void createIssue_sccess() {
    // given
    IssueCreateRequest request = new IssueCreateRequest(
        "왕이 넘어지면?",
        "King Kong ㅋㅋㅋ",
        List.of(1L, 2L),
        List.of(1L, 2L),
        1L
    );

    // when
    Long issueId = issueService.createIssue(request);

    // then
    Issue issue = issueRepository.findById(issueId).orElseThrow();
    assertThat(issue.getTitle()).isEqualTo("왕이 넘어지면?");

    List<IssueLabel> labels = issueLabelRepository.findByIssueId(issueId);
    assertThat(labels).hasSize(2);

    List<IssueAssignee> assignees = issueAssigneeRepository.findByIssueId(issueId);
    assertThat(assignees).hasSize(2);
  }
}
