package codesquad.team4.issuetracker.label;

import codesquad.team4.issuetracker.label.dto.LabelDto;
import codesquad.team4.issuetracker.label.dto.LabelDto.LabelInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LabelDao {

    private final JdbcTemplate jdbcTemplate;

    public List<LabelDto.LabelInfo> findLabelForFiltering() {
        String sql = "SELECT label_id, name, color FROM label";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                LabelDto.LabelInfo.builder()
                        .id(rs.getLong("label_id"))
                        .name(rs.getString("name"))
                        .color(rs.getString("color"))
                        .build()
        );
    }
}
