package elbin_bank.issue_tracker.issue.infrastructure.query;

import elbin_bank.issue_tracker.issue.application.query.repository.IssueQueryRepository;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.UserInfoProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueCountProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueDetailProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.projection.IssueProjection;
import elbin_bank.issue_tracker.issue.infrastructure.query.strategy.FilterStrategyContext;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class JdbcIssueQueryRepository implements IssueQueryRepository {

    private final NamedParameterJdbcTemplate jdbc;
    private final FilterStrategyContext filterCtx;

    @Override
    public List<IssueProjection> findIssues(String rawQuery) {
        String sql = """
                     SELECT i.id,
                            u.nickname AS author,
                            i.title,
                            m.title AS milestone,
                            i.is_closed,
                            i.created_at,
                            i.updated_at
                       FROM issue i
                  LEFT JOIN `user`    u ON i.author_id    = u.id
                  LEFT JOIN milestone m ON i.milestone_id = m.id
                WHERE i.deleted_at IS NULL
                """;

        var sqlAndParams = filterCtx.buildSql(rawQuery);
        String filterClause = sqlAndParams.sql();

        if (!filterClause.isBlank()) {
            sql = sql + " AND " + filterClause;
        }

        return jdbc.query(
                sql,
                sqlAndParams.params(),
                (rs, rn) -> new IssueProjection(
                        rs.getLong("id"),
                        rs.getString("author"),
                        rs.getString("title"),
                        rs.getBoolean("is_closed"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at") != null
                                ? rs.getTimestamp("updated_at").toLocalDateTime()
                                : null,
                        rs.getString("milestone")
                )
        );
    }

    @Override
    public Map<Long, List<String>> findAssigneeNamesByIssueIds(List<Long> issueIds) {
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
        return jdbc.queryForObject(
                "SELECT open_count, closed_count FROM issue_status_count WHERE id=1",
                Map.of(), (rs, rn) -> new IssueCountProjection(rs.getLong(1), rs.getLong(2))
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
                           m.total_issues      AS milestoneTotalIssues,
                           m.closed_issues     AS milestoneClosedIssues,
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
