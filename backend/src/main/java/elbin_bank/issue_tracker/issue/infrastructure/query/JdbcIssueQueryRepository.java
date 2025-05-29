package elbin_bank.issue_tracker.issue.infrastructure.query;

import elbin_bank.issue_tracker.issue.application.query.dsl.FilterCriteria;
import elbin_bank.issue_tracker.issue.application.query.repository.IssueQueryRepository;
import elbin_bank.issue_tracker.issue.domain.IssueState;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueCountProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueDetailProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.UserInfoProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JdbcIssueQueryRepository implements IssueQueryRepository {

    private final NamedParameterJdbcTemplate jdbc;
    private final SqlBuilder sqlBuilder;

    @Override
    public List<IssueProjection> findIssues(FilterCriteria crit) {
        String baseSql = """
                SELECT
                  i.id,
                  a.login       AS author,
                  i.title       AS title,
                  i.is_closed   AS isClosed,
                  i.created_at  AS createdAt,
                  i.updated_at  AS updatedAt,
                  m.title       AS milestone
                FROM issue i
                JOIN `user` a ON a.id = i.author_id
                LEFT JOIN milestone m ON m.id = i.milestone_id
                """;

        SqlClauseResult buildResult = sqlBuilder.build(crit);

        String sql = new StringBuilder()
                .append(baseSql)
                .append(buildResult.joinClause())
                .append(buildResult.whereClause())
                .append(" GROUP BY i.id")
                .append(buildResult.havingClause())
                .append(" ORDER BY i.id DESC")
                .toString();

        return jdbc.query(
                sql,
                buildResult.params(),
                (ResultSet rs, int rn) -> new IssueProjection(
                        rs.getLong("id"),
                        rs.getString("author"),
                        rs.getString("title"),
                        rs.getBoolean("isClosed"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("updatedAt").toLocalDateTime(),
                        rs.getString("milestone")
                )
        );
    }


    @Override
    public Map<Long, List<String>> findAssigneeNamesByIssueIds(List<Long> issueIds) {
        if (issueIds == null || issueIds.isEmpty()) {
            return Map.of();
        }

        String sql = """
                    SELECT a.issue_id, u.profile_image_url
                      FROM assignee a
                 JOIN `user` u ON u.id = a.user_id
                     WHERE a.issue_id IN (:ids)
                """;

        var params = new MapSqlParameterSource("ids", issueIds);
        return jdbc.query(
                        sql,
                        params,
                        (rs, rn) -> Map.entry(
                                rs.getLong("issue_id"),
                                rs.getString("profile_image_url")
                        )
                ).stream()
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));
    }

    @Override
    public List<UserInfoProjection> findAssigneesByIssueId(long id) {
        String sql = """
                    SELECT u.id            AS id,
                           u.nickname      AS nickname,
                           u.profile_image_url AS profileImage
                      FROM assignee a
                 JOIN `user` u ON a.user_id = u.id
                              AND u.deleted_at IS NULL
                     WHERE a.issue_id = :id
                """;

        var params = new MapSqlParameterSource("id", id);
        return jdbc.query(
                sql,
                params,
                (rs, rowNum) -> new UserInfoProjection(
                        rs.getLong("id"),
                        rs.getString("nickname"),
                        rs.getString("profileImage")
                )
        );
    }

    @Override
    public IssueCountProjection countIssueOpenAndClosed() {
        String sql = """
                    SELECT status_key, issue_count
                    FROM issue_status_count
                    WHERE status_key IN (:open, :closed)
                """;

        Map<String, Object> params = Map.of(
                "open", IssueState.OPEN.name().toLowerCase(),
                "closed", IssueState.CLOSED.name().toLowerCase()
        );

        List<Map<String, Object>> rows = jdbc.queryForList(sql, params);
        EnumMap<IssueState, Long> counts = new EnumMap<>(IssueState.class);

        for (Map<String, Object> row : rows) {
            String key = (String) row.get("status_key");
            long count = ((Number) row.get("issue_count")).longValue();

            IssueState.from(key).ifPresent(state -> counts.put(state, count));
        }

        return new IssueCountProjection(
                counts.getOrDefault(IssueState.OPEN, 0L),
                counts.getOrDefault(IssueState.CLOSED, 0L)
        );
    }

    @Override
    public Optional<IssueDetailProjection> findById(long id) {
        String sql = """
                    SELECT i.id,
                           i.author_id,
                           u.nickname          AS authorNickname,
                           u.profile_image_url AS authorProfileImage,
                           i.title,
                           i.contents,
                           i.milestone_id,
                           m.title             AS milestoneName,
                             COALESCE(m.total_issues, 0) AS milestoneTotalIssues,
                             COALESCE(m.closed_issues, 0) AS milestoneClosedIssues,
                           i.is_closed,
                           i.created_at,
                           i.updated_at
                      FROM issue i
                 JOIN `user` u  ON i.author_id = u.id AND u.deleted_at IS NULL
                 LEFT JOIN milestone m
                        ON i.milestone_id = m.id AND m.deleted_at IS NULL
                     WHERE i.id = :id
                       AND i.deleted_at IS NULL
                """;
        var params = new MapSqlParameterSource("id", id);

        try {
            IssueDetailProjection proj = jdbc.queryForObject(
                    sql, params,
                    (rs, rn) -> new IssueDetailProjection(
                            rs.getLong("id"),
                            rs.getLong("author_id"),
                            rs.getString("authorNickname"),
                            rs.getString("authorProfileImage"),
                            rs.getString("title"),
                            rs.getString("contents"),
                            rs.getObject("milestone_id", Long.class),
                            rs.getString("milestoneName"),
                            rs.getObject("milestoneTotalIssues", Long.class),
                            rs.getObject("milestoneClosedIssues", Long.class),
                            rs.getBoolean("is_closed"),
                            rs.getTimestamp("created_at").toLocalDateTime(),
                            rs.getTimestamp("updated_at") != null
                                    ? rs.getTimestamp("updated_at").toLocalDateTime()
                                    : null
                    )
            );

            return Optional.of(proj);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

}
