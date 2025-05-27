package elbin_bank.issue_tracker.label.infrastructure.command;

import elbin_bank.issue_tracker.label.domain.LabelCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcLabelCommandRepository implements LabelCommandRepository {

    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public void saveLabelsToIssue(Long issueId, List<Long> labelIds) {
        if (labelIds == null || labelIds.isEmpty()) return;

        String sql = """
                    INSERT IGNORE INTO issue_label (issue_id, label_id)
                    SELECT :issueId, l.id
                      FROM label l
                     WHERE l.id IN (:labelIds)
                """;

        var params = new MapSqlParameterSource()
                .addValue("issueId", issueId)
                .addValue("labelIds", labelIds);

        jdbc.update(sql, params);
    }

}
