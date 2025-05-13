package codesquad.team4.issuetracker.label.dto;

import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class LabelFilterDto {
    private List<IssueResponseDto.LabelInfo> labels;
    private int count;
}
