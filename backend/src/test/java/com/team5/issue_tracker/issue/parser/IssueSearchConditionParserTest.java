package com.team5.issue_tracker.issue.parser;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import com.team5.issue_tracker.issue.dto.request.IssueSearchRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class IssueSearchRequestParserTest {
  @Test
  @DisplayName("is:Open인 경우")
  void parseIsOpenTrue() {
    String searchCondition = "is:open";
    IssueSearchRequest IssueSearchRequest =
        IssueSearchRequestParser.fromQueryString(searchCondition);

    Boolean isOpen = IssueSearchRequest.getIsOpen();
    assertTrue(isOpen);
  }

  @Test
  @DisplayName("is:Closed 인 경우")
  void parseIsOpenFalse() {
    String searchCondition = "is:closed";
    IssueSearchRequest IssueSearchRequest =
        IssueSearchRequestParser.fromQueryString(searchCondition);

    Boolean isOpen = IssueSearchRequest.getIsOpen();
    assertFalse(isOpen);
  }

  @Test
  @DisplayName("is:open과 is:closed가 모두 있는 경우, 뒤에 오는 값이 우선한다.")
  void parseIsOpenAndIsClosed() {
    String searchCondition = "is:open is:closed";
    IssueSearchRequest IssueSearchRequest =
        IssueSearchRequestParser.fromQueryString(searchCondition);

    Boolean isOpen = IssueSearchRequest.getIsOpen();
    assertFalse(isOpen);
  }

  @Test
  @DisplayName("is:open과 is:closed가 모두 없는 경우, null이 반환된다.")
  void parseIsOpenAndIsClosedNone() {
    String searchCondition = "";
    IssueSearchRequest IssueSearchRequest =
        IssueSearchRequestParser.fromQueryString(searchCondition);

    Boolean isOpen = IssueSearchRequest.getIsOpen();
    assertNull(isOpen);
  }

  @Test
  @DisplayName("assignee:kim 인 경우")
  void parseAssignee() {
    String searchCondition = "assignee:kim";
    IssueSearchRequest IssueSearchRequest =
        IssueSearchRequestParser.fromQueryString(searchCondition);

    String assigneeName = IssueSearchRequest.getAssigneeName();
    assertThat(assigneeName).isEqualTo("kim");
  }

  @Test
  @DisplayName("assignee:kim과 assignee:lee가 모두 있는 경우, 뒤에 오는 값이 우선한다.")
  void parseAssigneeAndAssignee() {
    String searchCondition = "assignee:kim assignee:lee";
    IssueSearchRequest IssueSearchRequest =
        IssueSearchRequestParser.fromQueryString(searchCondition);

    String assigneeName = IssueSearchRequest.getAssigneeName();
    assertThat(assigneeName).isEqualTo("lee");
  }

  @Test
  @DisplayName("label:bug 인 경우")
  void parseLabel() {
    String searchCondition = "label:bug";
    IssueSearchRequest IssueSearchRequest =
        IssueSearchRequestParser.fromQueryString(searchCondition);

    String labelName = IssueSearchRequest.getLabelNames().iterator().next();
    assertThat(labelName).isEqualTo("bug");
  }

  @Test
  @DisplayName("label:bug과 label:feature가 모두 있는 경우, 둘 다 포함한다.")
  void parseLabelAndLabel() {
    String searchCondition = "label:bug label:feature";
    IssueSearchRequest IssueSearchRequest =
        IssueSearchRequestParser.fromQueryString(searchCondition);

    Set<String> labelNames = IssueSearchRequest.getLabelNames();
    assertThat(labelNames.size()).isEqualTo(2);
    assertThat( labelNames).contains("bug", "feature");
  }

  @Test
  @DisplayName("라벨이 없는 경우 빈 리스트를 반환한다.")
  void parseLabelNone() {
    String searchCondition = "";
    IssueSearchRequest IssueSearchRequest =
        IssueSearchRequestParser.fromQueryString(searchCondition);

    Set<String> labelNames = IssueSearchRequest.getLabelNames();
    assertThat(labelNames.size()).isEqualTo(0);
  }

  @Test
  @DisplayName("milestone:hard 인 경우")
  void parseMilestone() {
    String searchCondition = "milestone:hard";
    IssueSearchRequest IssueSearchRequest =
        IssueSearchRequestParser.fromQueryString(searchCondition);

    String milestoneName = IssueSearchRequest.getMilestoneName();
    assertThat(milestoneName).isEqualTo("hard");
  }

  @Test
  @DisplayName("milestone:hard과 milestone:easy가 모두 있는 경우, 뒤에 오는 값이 우선한다.")
  void parseMilestoneAndMilestone() {
    String searchCondition = "milestone:hard milestone:easy";
    IssueSearchRequest IssueSearchRequest =
        IssueSearchRequestParser.fromQueryString(searchCondition);

    String milestoneName = IssueSearchRequest.getMilestoneName();
    assertThat(milestoneName).isEqualTo("easy");
  }

  @Test
  @DisplayName("milestone이 없는 경우 null을 반환한다.")
  void parseMilestoneNone() {
    String searchCondition = "";
    IssueSearchRequest IssueSearchRequest =
        IssueSearchRequestParser.fromQueryString(searchCondition);

    String milestoneName = IssueSearchRequest.getMilestoneName();
    assertNull(milestoneName);
  }

  @Test
  @DisplayName("author:kim 인 경우")
  void parseAuthor() {
    String searchCondition = "author:kim";
    IssueSearchRequest IssueSearchRequest =
        IssueSearchRequestParser.fromQueryString(searchCondition);

    String authorName = IssueSearchRequest.getAuthorName();
    assertThat(authorName).isEqualTo("kim");
  }

  @Test
  @DisplayName("author:kim과 author:lee가 모두 있는 경우, 뒤에 오는 값이 우선한다.")
  void parseAuthorAndAuthor() {
    String searchCondition = "author:kim author:lee";
    IssueSearchRequest IssueSearchRequest =
        IssueSearchRequestParser.fromQueryString(searchCondition);

    String authorName = IssueSearchRequest.getAuthorName();
    assertThat(authorName).isEqualTo("lee");
  }

  @Test
  @DisplayName("author가 없는 경우 null을 반환한다.")
  void parseAuthorNone() {
    String searchCondition = "";
    IssueSearchRequest IssueSearchRequest =
        IssueSearchRequestParser.fromQueryString(searchCondition);

    String authorName = IssueSearchRequest.getAuthorName();
    assertNull(authorName);
  }

  @Test
  @DisplayName("여러 값들을 조합한 경우")
  void parseAll() {
    String searchCondition = "is:open assignee:kim label:bug milestone:hard author:lee";
    IssueSearchRequest IssueSearchRequest =
        IssueSearchRequestParser.fromQueryString(searchCondition);

    Boolean isOpen = IssueSearchRequest.getIsOpen();
    String assigneeName = IssueSearchRequest.getAssigneeName();
    Set<String> labelNames = IssueSearchRequest.getLabelNames();
    String milestoneName = IssueSearchRequest.getMilestoneName();
    String authorName = IssueSearchRequest.getAuthorName();

    assertTrue(isOpen);
    assertThat(assigneeName).isEqualTo("kim");
    assertThat(labelNames.size()).isEqualTo(1);
    assertThat(labelNames).contains("bug");
    assertThat(milestoneName).isEqualTo("hard");
    assertThat(authorName).isEqualTo("lee");
  }

  @Test
  @DisplayName("잘못된 값들은 무시하고, 나머지 값들만 파싱한다.")
  void parseInvalid() {
    String searchCondition = "is:open assignee:kim label:bug milestone:hard author:lee invalid:value";
    IssueSearchRequest IssueSearchRequest =
        IssueSearchRequestParser.fromQueryString(searchCondition);

    Boolean isOpen = IssueSearchRequest.getIsOpen();
    String assigneeName = IssueSearchRequest.getAssigneeName();
    Set<String> labelNames = IssueSearchRequest.getLabelNames();
    String milestoneName = IssueSearchRequest.getMilestoneName();
    String authorName = IssueSearchRequest.getAuthorName();

    assertTrue(isOpen);
    assertThat(assigneeName).isEqualTo("kim");
    assertThat(labelNames.size()).isEqualTo(1);
    assertThat(labelNames).contains("bug");
    assertThat(milestoneName).isEqualTo("hard");
    assertThat(authorName).isEqualTo("lee");
  }
}
