package codesquad.team4.issuetracker.label;

import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import codesquad.team4.issuetracker.label.dto.LabelFilterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelService {
    private final JdbcTemplate jdbcTemplate;

    public LabelFilterDto getFilterLabels() {
        String sql = "SELECT label_id, name, color FROM label";

        List<IssueResponseDto.LabelInfo> labels = jdbcTemplate.query(sql, (rs, rowNum) ->
               IssueResponseDto.LabelInfo.builder()
                       .id(rs.getLong("label_id"))
                       .name(rs.getString("name"))
                       .color(rs.getString("color"))
                       .build()
        );

        return LabelFilterDto.builder()
                .labels(labels)
                .count(labels.size())
                .build();
    }
}
