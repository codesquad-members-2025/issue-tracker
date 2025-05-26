package CodeSquad.IssueTracker.issue;

import lombok.Getter;
import CodeSquad.IssueTracker.home.dto.IssueFilterRequestDto;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

@Getter
public class IssueFilterQueryBuilder {

    private final StringBuilder whereClause = new StringBuilder();
    private final MapSqlParameterSource params = new MapSqlParameterSource();

    public IssueFilterQueryBuilder(Boolean isOpen, IssueFilterRequestDto filter) {
        whereClause.append(" WHERE 1=1 ");

        // 이슈 열림/닫힘 필터링
        if (isOpen != null) {
            whereClause.append("AND i.is_open = :isOpen ");
            params.addValue("isOpen", isOpen);
        }

        // 작성자 필터링
        if (filter.getAuthor() != null) {
            whereClause.append("AND i.author_id = :authorId ");
            params.addValue("authorId", filter.getAuthor());
        }

        // 마일스톤 필터링
        if (filter.getMilestone() != null) {
            whereClause.append("AND i.milestone_id = :milestoneId ");
            params.addValue("milestoneId", filter.getMilestone());
        }

        // 레이블 필터링
        if (filter.getLabel() != null) {
            whereClause.append("AND il.label_id = :labelId ");
            params.addValue("labelId", filter.getLabel());
        }

        // 담당자 필터링
        if (filter.getAssignee() != null) {
            whereClause.append("AND ia.assignee_id = :assigneeId ");
            params.addValue("assigneeId", filter.getAssignee());
        }

        // 댓글 남긴 이슈 필터링
        if (filter.getCommentedBy() != null) {
            whereClause.append("AND c.author_id = :commentedBy ");
            params.addValue("commentedBy", filter.getCommentedBy());
        }
    }
}
