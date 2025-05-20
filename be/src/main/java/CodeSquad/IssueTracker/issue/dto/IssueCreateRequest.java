package CodeSquad.IssueTracker.issue.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class IssueCreateRequest {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "이슈의 내용은 공백일 수 없습니다.")
    private String content;

    private List<Long> assigneeIds; // null 가능

    private List<Long> labelIds;   // 빈 리스트 가능

    private Long milestoneId;      // null 가능
}