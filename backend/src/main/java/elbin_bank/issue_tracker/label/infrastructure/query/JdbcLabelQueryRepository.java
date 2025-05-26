package elbin_bank.issue_tracker.label.infrastructure.query;

import elbin_bank.issue_tracker.label.application.query.repository.LabelQueryRepository;
import elbin_bank.issue_tracker.label.infrastructure.query.projection.LabelProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JdbcLabelQueryRepository implements LabelQueryRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public Map<Long, List<LabelProjection>> findByIssueIds(List<Long> issueIds) {
        String sql = """
                    SELECT il.issue_id, l.id, l.name, l.description, l.color
                      FROM issue_label il
                 JOIN label l ON l.id = il.label_id
                     WHERE il.issue_id IN (:ids)
                """;

        var params = new MapSqlParameterSource("ids", issueIds);
        return jdbc.query(
                        sql,
                        params,
                        (rs, rn) -> Map.entry(
                                rs.getLong("issue_id"),
                                new LabelProjection(
                                        rs.getLong("id"),
                                        rs.getString("name"),
                                        rs.getString("color"),
                                        rs.getString("description")
                                )
                        )
                ).stream()
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));

    }

}
