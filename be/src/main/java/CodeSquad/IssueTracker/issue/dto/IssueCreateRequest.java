package CodeSquad.IssueTracker.issue.dto;

import lombok.Data;

import java.util.List;

@Data
public class IssueCreateRequest {

    private String title;
    private String content;
    private List<Long> assigneeIds; // null 가능
    private List<Long> labelIds;   // 빈 리스트 가능
    private Long milestoneId;      // null 가능
    private List<String> imageUrls;
}