package CodeSquad.IssueTracker.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponseDto<T> {

    private final boolean success;
    private final Object message; // String or Map<String, String>
    private final T data;

    public static <T> BaseResponseDto<T> success(Object message, T data) {
        return new BaseResponseDto<>(true, message, data);
    }

    public static <T> BaseResponseDto<T> failure(Object message, T data) {
        return new BaseResponseDto<>(false, message, data);
    }

    public static <T> BaseResponseDto<T> failure(Object message) {
        return new BaseResponseDto<>(false, message, null);
    }
}
