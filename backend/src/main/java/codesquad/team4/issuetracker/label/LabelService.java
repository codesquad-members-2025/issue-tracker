package codesquad.team4.issuetracker.label;

import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import codesquad.team4.issuetracker.label.dto.LabelDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelService {
    private final JdbcTemplate jdbcTemplate;

    public LabelDto.LabelFilter getFilterLabels() {
        String sql = "SELECT label_id, name, color FROM label";

        List<LabelDto.LabelInfo> labels = jdbcTemplate.query(sql, (rs, rowNum) ->
                LabelDto.LabelInfo.builder()
                       .id(rs.getLong("label_id"))
                       .name(rs.getString("name"))
                       .color(rs.getString("color"))
                       .build()
        );

        return LabelDto.LabelFilter.builder()
                .labels(labels)
                .count(labels.size())
                .build();
    }
}
