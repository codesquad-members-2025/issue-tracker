package codesquad.team01.issuetracker.label.repository.impl;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import codesquad.team01.issuetracker.label.domain.LabelTextColor;
import codesquad.team01.issuetracker.label.dto.LabelDto;
import codesquad.team01.issuetracker.label.repository.LabelQueryRepository;
import lombok.RequiredArgsConstructor;

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
		        l.text_is_black as label_text_is_black
		    FROM issue_label il
		    JOIN label l ON il.label_id = l.id
		    WHERE il.issue_id IN (:issueIds)
		""";

	private final RowMapper<LabelDto.IssueLabelRow> labelRowMapper = (rs, rowNum) -> LabelDto.IssueLabelRow.builder()
		.issueId(rs.getInt("issue_id"))
		.labelId(rs.getInt("label_id"))

		.labelName(rs.getString("label_name"))
		.labelColor(rs.getString("label_color"))
		.labelTextColor(LabelTextColor.fromTextColorStr(rs.getString("label_text_color")))
		.build();

	@Override
	public List<LabelDto.IssueLabelRow> findLabelsByIssueIds(List<Integer> issueIds) {

		if (issueIds.isEmpty()) {
			return List.of();
		}
		MapSqlParameterSource params = new MapSqlParameterSource("issueIds", issueIds);
		return jdbcTemplate.query(LABEL_QUERY, params, labelRowMapper);
	}
}
