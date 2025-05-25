package codesquad.team4.issuetracker.label.dto;

import codesquad.team4.issuetracker.milestone.dto.MilestoneDto;
import codesquad.team4.issuetracker.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.Set;

public class LabelResponseDto {

    @AllArgsConstructor
    @Getter
    @Builder
    @EqualsAndHashCode
    public static class LabelInfo {
        private Long id;
        private String name;
        private String color;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class LabelFilter {
        private List<LabelInfo> labels;
        private int count;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class LabelListDto {
        private List<LabelDto> labels;
    }

    @AllArgsConstructor
    @Getter
    @Builder
    public static class LabelDto {
        private Long id;
        private String name;
        private String description;
        private String color;
    }

}
