package elbin_bank.issue_tracker.label.infrastructure.command;

import elbin_bank.issue_tracker.label.domain.Label;
import elbin_bank.issue_tracker.label.domain.LabelCommandRepository;
import elbin_bank.issue_tracker.label.infrastructure.query.projection.LabelProjection;
import elbin_bank.issue_tracker.label.presentation.command.dto.request.LabelUpdateRequestDto;
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

    @Override
    public void deleteLabelsFromIssue(Long issueId, List<Long> labelIds) {
        if (labelIds == null || labelIds.isEmpty()) {
            return;
        }

        String sql = """
                    DELETE FROM issue_label
                     WHERE issue_id = :issueId
                       AND label_id IN (:labelIds)
                """;

        var params = new MapSqlParameterSource()
                .addValue("issueId", issueId)
                .addValue("labelIds", labelIds);

    public void save(Label label) {
        String sql = """
                    INSERT INTO label
                      (name, description, color)
                    VALUES
                      (:name, :description, :color)
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", label.getName())
                .addValue("description", label.getDescription())
                .addValue("color", label.getColor());

        jdbc.update(sql, params);
    }

    @Override
    public void update(LabelProjection label, LabelUpdateRequestDto labelUpdateRequestDto) {
        String sql = """
                    UPDATE label
                       SET name = :name, description = :description, color = :color, updated_at = NOW()
                    WHERE id = :id
                """;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", labelUpdateRequestDto.name())
                .addValue("description", labelUpdateRequestDto.description())
                .addValue("color", labelUpdateRequestDto.color())
                .addValue("id", label.id());

        jdbc.update(sql, params);
    }

}
