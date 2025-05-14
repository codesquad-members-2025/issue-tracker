package elbin_bank.issue_tracker.issue.application.query.dto;

import elbin_bank.issue_tracker.label.application.query.dto.LabelsDto;

import java.time.LocalDateTime;
import java.util.List;

public record IssueSummaryDto(
        Long id,
        String author,
        String title,
        List<LabelsDto> labels,
        boolean isClosed,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<String> assigneesProfileImages,
        String milestone
) {
}
