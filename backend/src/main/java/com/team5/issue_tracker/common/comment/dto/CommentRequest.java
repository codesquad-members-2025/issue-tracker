package com.team5.issue_tracker.common.comment.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
  @NotBlank(message = "댓글 내용을 입력해주세요.")
  private String content;
  private List<String> attachments;
}
