package codesquad.team4.issuetracker.label;

import codesquad.team4.issuetracker.label.dto.LabelDto;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LabelDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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

    public void deleteAllIssueLabelByIssueId(Long issueId) {
        String sql = "DELETE FROM issue_label WHERE issue_id = :issueId";
        Map<String, Object> params = Map.of("issueId", issueId);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public List<Long> findExistingLabelIds(Set<Long> labelIds) {
        String sql = "SELECT label_id FROM label WHERE label_id IN (:ids)";
        Map<String, Object> params = Map.of("ids", labelIds);

        return namedParameterJdbcTemplate.queryForList(sql, params, Long.class);
    }
}
