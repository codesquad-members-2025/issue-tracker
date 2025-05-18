package com.team5.issue_tracker.issue.service;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.team5.issue_tracker.issue.domain.Issue;
import com.team5.issue_tracker.issue.dto.request.IssueCreateRequest;
import com.team5.issue_tracker.issue.repository.IssueRepository;
import com.team5.issue_tracker.user.service.UserService;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IssueServiceTest {

  @InjectMocks
  private IssueService issueService; // 실제로 테스트를 진행하는 객체에는 Inject를 사용

  @Mock
  private IssueRepository issueRepository;

  @Mock
  private UserService userService;

  @Test
  @DisplayName("정상 요청에 이슈가 생성되어야 한다.")
  void createIssue_success_정상_요청() {
    // given
    IssueCreateRequest request = new IssueCreateRequest(
        "hello World",
        "java",
        1L,
        List.of(1L, 2L),
        null
    );

    Long userId = 1L;

    when(issueRepository.save(any(Issue.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    // when
    Issue saved = issueService.createIssue(request, userId);

    // then
    assertThat(saved).isNotNull();
    verify(issueRepository).save(any(Issue.class));
  }

  @Test
  @DisplayName("작성자가 존재하지 않으면 예외가 발생한다")
  void createIssue_fail_존재하지_않는_사용자_요청() {
    // given
    IssueCreateRequest request = new IssueCreateRequest(
        "hello World",
        "java",
        1L,
        List.of(1L, 2L),
        null
    );

    Long userId = 9999L; // 존재하지 않는 ID

    when(userService.existsById(userId)).thenReturn(false);

    //when & then
    assertThatThrownBy(() -> issueService.createIssue(request, userId))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("작성자가 존재하지 않습니다.");
  }

  @Test
  @DisplayName("제목이 null이면 예외가 발생한다")
  void createIssue_fail_제목이_null() {
    //given
    IssueCreateRequest request = new IssueCreateRequest(
        null,
        "java",
        1L,
        List.of(1L, 2L),
        null
    );

    Long userId = 1L;

    when(userService.existsById(userId)).thenReturn(true);

    //when&then
    assertThatThrownBy(() -> issueService.createIssue(request, userId))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("제목을 작성해주세요.");
  }

  @Test
  @DisplayName("본문이 null이면 예외가 발생한다")
  void createIssue_fail_본문이_null() {
    //given
    IssueCreateRequest request = new IssueCreateRequest(
        "hello World",
        " ", // null일 때도 가능
        1L,
        List.of(1L, 2L),
        null
    );

    Long userId = 1L;

    when(userService.existsById(userId)).thenReturn(true);

    //when&then
    assertThatThrownBy(() -> issueService.createIssue(request, userId))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("본문을 작성해주세요.");
  }

}
