package elbin_bank.issue_tracker.comment.application.query.dto;

import java.util.List;

public record CommentsResponseDto(List<CommentDto> comments) {
}
