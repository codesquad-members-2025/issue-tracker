package CodeSquad.IssueTracker.issue;

import CodeSquad.IssueTracker.home.dto.IssueFilterRequestDto;
import CodeSquad.IssueTracker.issue.dto.FilteredIssueDto;
import CodeSquad.IssueTracker.issue.dto.IssueUpdateDto;
import CodeSquad.IssueTracker.milestone.dto.SummaryMilestoneDto;
import CodeSquad.IssueTracker.user.dto.SummaryUserDto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateIssueRepository implements IssueRepository {

    public static final int LIMIT_SIZE = 20;

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateIssueRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("issues")
                .usingGeneratedKeyColumns("issue_id");
    }

    @Override
    public Issue save(Issue issue) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(issue);
        Number key = jdbcInsert.executeAndReturnKey(param);
        issue.setIssueId((key.longValue()));
        return issue;
    }

    @Override
    public void update(Long issueId, IssueUpdateDto updateParam) {
        String sql = "UPDATE issues SET title = :title, content = :content, is_Open = :isOpen, timestamp = :timestamp, assignee_Id = :assigneeId, milestone_Id = :milestoneId WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("title", updateParam.getTitle())
                .addValue("content", updateParam.getContent())
                .addValue("isOpen", updateParam.getIsOpen())
                .addValue("timestamp", updateParam.getTimestamp())
                .addValue("milestoneId", updateParam.getMilestoneId())
                .addValue("assigneeId", updateParam.getAssigneeId())
                .addValue("id", issueId);
        template.update(sql, param);
    }

    @Override
    public Optional<Issue> findById(Long issueId) {
        String sql = "SELECT * FROM issues WHERE issue_Id = :issueId";
        Map<String, Object> param = Map.of("issueId", issueId);
        Issue issue = template.queryForObject(sql, param, issueRowMapper());
        return Optional.ofNullable(issue);
    }

    @Override
    public List<Issue> findAll() {
        String sql = "SELECT * FROM issues";
        return template.query(sql, issueRowMapper());
    }

    @Override
    public List<FilteredIssueDto> findIssuesByFilter(int page, IssueFilterRequestDto filterRequestDto) {
        StringBuilder issueSql = new StringBuilder();
        issueSql.append("SELECT DISTINCT i.issue_id, i.title, i.is_open, i.author_id, u.nick_name, i.milestone_id, m.name AS milestone_name, i.last_modified_at\n")
                .append("FROM issues i\n")
                .append("LEFT JOIN milestones m ON i.milestone_id = m.milestone_id\n")
                .append("LEFT JOIN users u ON i.author_id = u.id\n")
                .append("LEFT JOIN issue_assignee ia ON i.issue_id = ia.issue_id\n")
                .append("LEFT JOIN issue_label il ON i.issue_id = il.issue_id\n")
                .append("LEFT JOIN comments c ON i.issue_id = c.issue_id\n");

        boolean hasWhere = false;
        MapSqlParameterSource params = new MapSqlParameterSource();

        // 이슈 열림/닫힘 상태 필터링
        if (filterRequestDto.getIsOpen() != null) {
            issueSql.append("WHERE ");
            issueSql.append("i.is_open = :isOpen ");
            params.addValue("isOpen", filterRequestDto.getIsOpen());
            hasWhere = true;
        }

        // 작성자 필터링
        if (filterRequestDto.getAuthor() != null) {
            issueSql.append(hasWhere ? "AND " : "WHERE ");
            issueSql.append("i.author_id = :authorId ");
            params.addValue("authorId", filterRequestDto.getAuthor());
            hasWhere = true;
        }

        // 마일스톤 필터링
        if (filterRequestDto.getMilestone() != null) {
            issueSql.append(hasWhere ? "AND " : "WHERE ");
            issueSql.append("i.milestone_id = :milestoneId");
            params.addValue("milestoneId", filterRequestDto.getMilestone());
            hasWhere = true;
        }

        // 레이블 필터링
        if (filterRequestDto.getLabel() != null) {
            issueSql.append(hasWhere ? "AND " : "WHERE ");
            issueSql.append("il.label_id = :labelId");
            params.addValue("labelId", filterRequestDto.getLabel());
            hasWhere = true;
        }

        // 담당자 필터링
        if (filterRequestDto.getAssignee() != null) {
            issueSql.append(hasWhere ? "AND " : "WHERE ");
            issueSql.append("ia.assignee_id = :assigneeId");
            params.addValue("assigneeId", filterRequestDto.getAssignee());
            hasWhere = true;
        }

        // 댓글 남긴 이슈 필터링
        if (filterRequestDto.getCommentedBy() != null) {
            issueSql.append(hasWhere ? "AND " : "WHERE ");
            issueSql.append("c.author_id = :commentedBy");
            params.addValue("commentedBy", filterRequestDto.getCommentedBy());
        }

        issueSql.append(" ORDER BY i.issue_id DESC\n");
        issueSql.append("LIMIT :limitSize OFFSET :page");
        params.addValue("limitSize", LIMIT_SIZE);
        params.addValue("page", (page - 1) * LIMIT_SIZE);

        List<FilteredIssueDto> issues = template.query(issueSql.toString(), params, (rs, rowNum) -> {
            FilteredIssueDto dto = new FilteredIssueDto();
            dto.setIssueId(rs.getLong("issue_id"));
            dto.setTitle(rs.getString("title"));
            dto.setIsOpen(rs.getBoolean("is_open"));
            dto.setAuthor(new SummaryUserDto(rs.getLong("author_id"), rs.getString("nick_name")));
            dto.setMilestone(new SummaryMilestoneDto(rs.getLong("milestone_id"), rs.getString("milestone_name")));
            dto.setLastModifiedAt(rs.getTimestamp("last_modified_at").toLocalDateTime());
            return dto;
        });

        return issues;
    }

    @Override
    public int countFilteredIssues(IssueFilterRequestDto filterRequestDto) {
        StringBuilder countSql = new StringBuilder();
        countSql.append("SELECT COUNT(DISTINCT i.issue_id) ")  // DISTINCT로 중복 제거
                .append("FROM issues i ")
                .append("LEFT JOIN milestones m ON i.milestone_id = m.milestone_id ")
                .append("LEFT JOIN users u ON i.author_id = u.id ")
                .append("LEFT JOIN issue_assignee ia ON i.issue_id = ia.issue_id ")
                .append("LEFT JOIN issue_label il ON i.issue_id = il.issue_id ")
                .append("LEFT JOIN comments c ON i.issue_id = c.issue_id ");

        boolean hasWhere = false;
        MapSqlParameterSource params = new MapSqlParameterSource();

        // 이슈 열림/닫힘 상태 필터링
        if (filterRequestDto.getIsOpen() != null) {
            countSql.append("WHERE ");
            countSql.append("i.is_open = :isOpen ");
            params.addValue("isOpen", filterRequestDto.getIsOpen());
            hasWhere = true;
        }

        // 작성자 필터링
        if (filterRequestDto.getAuthor() != null) {
            countSql.append(hasWhere ? "AND " : "WHERE ");
            countSql.append("i.author_id = :authorId ");
            params.addValue("authorId", filterRequestDto.getAuthor());
            hasWhere = true;
        }

        // 마일스톤 필터링
        if (filterRequestDto.getMilestone() != null) {
            countSql.append(hasWhere ? "AND " : "WHERE ");
            countSql.append("i.milestone_id = :milestoneId");
            params.addValue("milestoneId", filterRequestDto.getMilestone());
            hasWhere = true;
        }

        // 레이블 필터링
        if (filterRequestDto.getLabel() != null) {
            countSql.append(hasWhere ? "AND " : "WHERE ");
            countSql.append("il.label_id = :labelId");
            params.addValue("labelId", filterRequestDto.getLabel());
            hasWhere = true;
        }

        // 담당자 필터링
        if (filterRequestDto.getAssignee() != null) {
            countSql.append(hasWhere ? "AND " : "WHERE ");
            countSql.append("ia.assignee_id = :assigneeId");
            params.addValue("assigneeId", filterRequestDto.getAssignee());
            hasWhere = true;
        }

        // 댓글 남긴 이슈 필터링
        if (filterRequestDto.getCommentedBy() != null) {
            countSql.append(hasWhere ? "AND " : "WHERE ");
            countSql.append("c.author_id = :commentedBy");
            params.addValue("commentedBy", filterRequestDto.getCommentedBy());
        }

        return template.queryForObject(countSql.toString(), params, Integer.class);
    }


    private RowMapper<Issue> issueRowMapper() {
        return BeanPropertyRowMapper.newInstance(Issue.class);
    }
}
