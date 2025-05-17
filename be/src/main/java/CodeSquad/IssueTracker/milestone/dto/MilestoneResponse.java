package CodeSquad.IssueTracker.milestone.dto;

import lombok.Data;

@Data
public class MilestoneResponse {
    private Long milestoneId;
    private String name;
    private String description; // 프론트에서 필요 없다면 제거 가능
    private String endDate; // ISO 8601 문자열로 포맷 (예: "2025-05-16")
    private Long processingRate; // 0~100 값
    private Boolean isOpen;
}
