package codesquad.team4.issuetracker.user;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AssigneeDao {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public void deleteAllByIssueId(Long issueId) {
        String sql = "DELETE FROM issue_assignee WHERE issue_id = :issueId";
        Map<String, Object> params = Map.of("issueId", issueId);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
