package elbin_bank.issue_tracker.issue.infrastructure;

import elbin_bank.issue_tracker.issue.domain.Issue;
import elbin_bank.issue_tracker.issue.domain.IssueRepository;
import elbin_bank.issue_tracker.issue.domain.IssueStatus;
import elbin_bank.issue_tracker.label.domain.Label;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class JdbcIssueRepository implements IssueRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public JdbcIssueRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Issue> findByFilter(Boolean isClosed) {
        IssueQueryBuilder builder = new IssueQueryBuilder()
                .filterClosed(isClosed);
        return jdbc.query(
                builder.buildSql(),
                builder.getParams(),
                BeanPropertyRowMapper.newInstance(Issue.class)
        );
    }

    @Override
    public long countByStatus(IssueStatus status) {
        String sql = "SELECT COUNT(*) FROM issue WHERE is_closed = :st";
        Long count = jdbc.queryForObject(
                sql,
                new MapSqlParameterSource("st", status == IssueStatus.CLOSED),
                Long.class
        );

        if (count == null) {
            return 0L;
        }

        return count;
    }

    @Override
    public Map<Long, List<Label>> findLabelsByIssueIds(List<Long> issueIds) {
        if (issueIds == null || issueIds.isEmpty()) {
            return Map.of();
        }
        String sql = """
                SELECT il.issue_id      AS issueId,
                       l.id             AS id,
                       l.name           AS name,
                       l.color          AS color,
                       l.description    AS description
                  FROM issue_label il
                  JOIN label l ON l.id = il.label_id
                 WHERE il.issue_id IN (:ids)
                """;
        var params = new MapSqlParameterSource("ids", issueIds);

        List<AbstractMap.SimpleEntry<Long, Label>> rows = jdbc.query(
                sql, params,
                (rs, rn) -> new AbstractMap.SimpleEntry<>(
                        rs.getLong("issueId"),
                        new Label(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("color"),
                                rs.getString("description")
                        )
                )
        );

        return rows.stream()
                .collect(Collectors.groupingBy(
                        AbstractMap.SimpleEntry::getKey,
                        Collectors.mapping(AbstractMap.SimpleEntry::getValue, Collectors.toList())
                ));
    }

    @Override
    public Map<Long, List<String>> findAssigneesByIssueIds(List<Long> issueIds) {
        if (issueIds == null || issueIds.isEmpty()) {
            return Map.of();
        }
        String sql = """
                SELECT a.issue_id          AS issueId,
                       u.profile_image_url AS imageUrl
                  FROM assignee a
                  JOIN user u ON u.id = a.user_id
                 WHERE a.issue_id IN (:ids)
                """;
        var params = new MapSqlParameterSource("ids", issueIds);

        List<AbstractMap.SimpleEntry<Long, String>> rows = jdbc.query(
                sql, params,
                (rs, rn) -> new AbstractMap.SimpleEntry<>(
                        rs.getLong("issueId"),
                        rs.getString("imageUrl")
                )
        );

        return rows.stream()
                .collect(Collectors.groupingBy(
                        AbstractMap.SimpleEntry::getKey,
                        Collectors.mapping(AbstractMap.SimpleEntry::getValue, Collectors.toList())
                ));
    }
}
