package codesquad.team4.issuetracker.label.dto;

import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

public class LabelDto {

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
}
