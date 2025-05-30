package com.team5.issue_tracker.label.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LabelRequest {

  @NotBlank(message = "라벨 이름을 작성해주세요.")
  private String name;

  private String description;

  @NotBlank(message = "텍스트 색을 넣어주세요.")
  private String textColor;

  @NotBlank(message = "배경 색을 입혀주세요.")
  private String backgroundColor;
}
