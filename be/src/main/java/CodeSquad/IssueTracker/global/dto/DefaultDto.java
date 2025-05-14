package CodeSquad.IssueTracker.global.dto;

import lombok.Data;

@Data
public class DefaultDto {
    private final boolean success;
    private final String message;
    private final Object data;
}
