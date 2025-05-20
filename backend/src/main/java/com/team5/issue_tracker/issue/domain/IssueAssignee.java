package com.team5.issue_tracker.issue.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;

@Getter
@Table("issue_assignee")
public class IssueAssignee {

  @Id
  private Long id;

  private final Long issueId;
  private final Long assigneeId; //assignee id를 final로 한 이유는 변경시 삭제후 삽입방식으로 하려고 합니다.

  public IssueAssignee(Long issueId, Long assigneeId) {
    this.issueId = issueId;
    this.assigneeId = assigneeId;
  }
}
