package codesquad.team4.issuetracker.label.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class LabelRequestDto {
    @AllArgsConstructor
    @Builder
    @Getter
    public static class CreateLabelDto {
        @NotBlank
        private String name;
        private String description;
        @NotBlank
        private String color;
    }
}
