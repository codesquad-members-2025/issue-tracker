package com.team5.issue_tracker.issue.dto.request;

import java.util.List;

import com.team5.issue_tracker.common.comment.dto.CommentRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IssueCreateRequest {

  @NotBlank(message = "제목을 작성해주세요.")
  private String title;

  @NotNull(message = "댓글 내용을 넣어주세요.")
  private CommentRequest coment;

  @NotNull(message = "담당자를 넣어주세요.")
  private List<Long> assigneeIds;

  @NotNull(message = "라벨을 넣어주세요.")
  private List<Long> labelIds;
  private Long milestoneId;
}
