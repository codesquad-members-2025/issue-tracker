package codesquad.team01.issuetracker.label.repository.impl;

import codesquad.team01.issuetracker.label.dto.LabelDto;
import codesquad.team01.issuetracker.label.repository.LabelQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class JdbcLabelQueryRepository implements LabelQueryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String LABEL_QUERY = """
        SELECT 
            il.issue_id,
            l.id as label_id,
            l.name as label_name,
            l.color as label_color,
            l.text_color as label_text_color
        FROM issue_label il
        JOIN label l ON il.label_id = l.id
        WHERE il.issue_id IN (:issueIds)
        """;

    private final RowMapper<LabelDto.IssueLabelRow> labelRowMapper = (rs, rowNum) -> LabelDto.IssueLabelRow.builder()
            .issueId(rs.getLong("issue_id"))
            .labelId(rs.getLong("label_id"))
            .labelName(rs.getString("label_name"))
            .labelColor(rs.getString("label_color"))
            .labelTextColor(rs.getString("label_text_color"))
            .build();

    @Override
    public List<LabelDto.IssueLabelRow> findLabelsByIssueIds(List<Long> issueIds) {
        if (issueIds.isEmpty()) {
            return List.of();
        }
        MapSqlParameterSource params = new MapSqlParameterSource("issueIds", issueIds);
        return jdbcTemplate.query(LABEL_QUERY, params, labelRowMapper);
    }
}
