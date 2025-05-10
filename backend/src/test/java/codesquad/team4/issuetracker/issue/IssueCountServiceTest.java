package codesquad.team4.issuetracker.issue;

import codesquad.team4.issuetracker.issue.dto.IssueCountDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional //test 후 자동 rollback
public class IssueCountServiceTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IssueCountService issueCountService;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("INSERT INTO issue (issue_id, title, is_open, author_id, create_at, updated_at) VALUES (1, open1, true, 1, NOW(), NOW())");
        jdbcTemplate.update("INSERT INTO issue(issue_id, title, is_open, author_id, create_at, updated_at) VALUES (2, open2, true, 1, NOW(), NOW())");
        jdbcTemplate.update("INSERT INTO issue(issue_id, title, is_open, author_id, create_at, updated_at) VALUES (3, closed1, false, 1, NOW(), NOW())");
    }

    @Test
    @DisplayName("이슈 상태별 개수 반환")
    void 이슈_상태별_개수_반환() {}
    IssueCountDTO result = issueCountService.getIssueCounts();

    assertThat(result.getOpenCount()).isEqualTo(2);
    assertThat(result.getClosedCount()).isEqualTo(1);

}
