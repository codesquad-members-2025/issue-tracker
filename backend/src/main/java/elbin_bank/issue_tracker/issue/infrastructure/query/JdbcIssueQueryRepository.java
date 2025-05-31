package elbin_bank.issue_tracker.issue.infrastructure.query;

import elbin_bank.issue_tracker.issue.application.query.dsl.FilterCriteria;
import elbin_bank.issue_tracker.issue.application.query.repository.IssueQueryRepository;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueDetailProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.UserInfoProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
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
        SqlClauseResult sqlClauseResult = sqlBuilder.build(crit);

        String sql = """
                WITH
                  base AS (
                    SELECT
                      i.id,
                      au.nickname    AS author,
                      i.title        AS title,
                      i.is_closed    AS isClosed,
                      i.created_at   AS createdAt,
                      i.updated_at   AS updatedAt,
                      m.title        AS milestone
                    FROM issue i
                    JOIN `user` au
                      ON au.id = i.author_id
                     AND au.deleted_at IS NULL
                    LEFT JOIN milestone m
                      ON m.id = i.milestone_id
                     AND m.deleted_at IS NULL
                """
                + sqlClauseResult.joinClause() + "\n"
                + sqlClauseResult.whereClause() + " AND i.deleted_at IS NULL\n"
                + "    GROUP BY i.id\n"
                + sqlClauseResult.havingClause() +
                """
                                  ),
                                  counts AS (
                                    SELECT
                                      SUM(CASE WHEN isClosed = FALSE THEN 1 ELSE 0 END) AS openCount,
                                      SUM(CASE WHEN isClosed = TRUE  THEN 1 ELSE 0 END) AS closedCount
                                    FROM base
                                  ),
                                  filtered AS (
                                    SELECT * 
                                    FROM base 
                                    WHERE isClosed = :isClosed
                                  )
                                SELECT
                                  f.id,
                                  f.author,
                                  f.title,
                                  f.isClosed,
                                  f.createdAt,
                                  f.updatedAt,
                                  f.milestone,
                                  c.openCount,
                                  c.closedCount
                                FROM filtered f
                                CROSS JOIN counts c
                                ORDER BY f.id DESC
                        """;

        return jdbc.query(
                sql,
                sqlClauseResult.params(),
                (ResultSet rs, int rowNum) -> new IssueProjection(
                        rs.getLong("id"),
                        rs.getString("author"),
                        rs.getString("title"),
                        rs.getBoolean("isClosed"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("updatedAt") != null
                                ? rs.getTimestamp("updatedAt").toLocalDateTime()
                                : null,
                        rs.getString("milestone"),
                        rs.getLong("openCount"),
                        rs.getLong("closedCount")
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
                AND u.deleted_at IS NULL
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
    public Optional<IssueDetailProjection> findById(long id) {
        String sql = """
                SELECT
                  i.id                           AS id,
                  i.author_id                    AS authorId,
                  u.nickname                     AS authorNickname,
                  u.profile_image_url            AS authorProfileImage,
                  i.title                        AS title,
                  i.contents                     AS contents,
                  i.is_closed                    AS isClosed,
                  i.created_at                   AS createdAt,
                  i.updated_at                   AS updatedAt
                FROM issue i
                JOIN `user` u
                  ON i.author_id = u.id
                 AND u.deleted_at IS NULL
                WHERE i.id = :id
                  AND i.deleted_at IS NULL
                """;

        var params = new MapSqlParameterSource("id", id);

        try {
            IssueDetailProjection proj = jdbc.queryForObject(
                    sql,
                    params,
                    (rs, rowNum) -> new IssueDetailProjection(
                            rs.getLong("id"),
                            rs.getLong("authorId"),
                            rs.getString("authorNickname"),
                            rs.getString("authorProfileImage"),
                            rs.getString("title"),
                            rs.getString("contents"),
                            rs.getBoolean("isClosed"),
                            rs.getTimestamp("createdAt").toLocalDateTime(),
                            rs.getTimestamp("updatedAt") != null
                                    ? rs.getTimestamp("updatedAt").toLocalDateTime()
                                    : null
                    )
            );

            return Optional.of(proj);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<UserInfoProjection> findAssigneeByIssueId(long id) {
        String sql = """
                SELECT u.id AS id, 
                       u.nickname AS nickname,
                       u.profile_image_url AS profileImage
                FROM assignee a
                JOIN `user` u ON a.user_id = u.id
                WHERE a.issue_id = :id
                AND u.deleted_at IS NULL
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

}
