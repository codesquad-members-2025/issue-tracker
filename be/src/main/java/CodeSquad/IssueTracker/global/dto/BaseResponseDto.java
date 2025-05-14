package CodeSquad.IssueTracker.global.dto;

import lombok.Data;

@Data
public class BaseResponseDto<T> {
    private final boolean success;
    private final String message;
    private final T data;

    public static <T> BaseResponseDto<T> success(String message, T data) {
        return new BaseResponseDto<>(true, message, data);
    }

    public static <T> BaseResponseDto<T> failure(String message) {
        return new BaseResponseDto<>(false, message, null);
    }
}
