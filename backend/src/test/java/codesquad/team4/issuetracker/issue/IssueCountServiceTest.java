package codesquad.team4.issuetracker.issue;

import codesquad.team4.issuetracker.issue.dto.IssueCountDto;
import codesquad.team4.issuetracker.util.TestDataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles("test")
class IssueCountServiceTest {

    @Autowired
    private IssueDao issueDao;

    @Autowired
    private IssueService issueService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        TestDataHelper.insertUser(jdbcTemplate, 1L, "사용자1");
        TestDataHelper.insertIssue(jdbcTemplate, 1L, "이슈 1", true, 1L);   // open
        TestDataHelper.insertIssue(jdbcTemplate, 2L, "이슈 2", true, 1L);   // open
        TestDataHelper.insertIssue(jdbcTemplate, 3L, "이슈 3", false, 1L);  // closed
    }


    @Test
    @DisplayName("이슈 상태별 개수 반환")
    void 이슈_상태별_개수_반환() {
        // when
        IssueCountDto result = issueService.getIssueCounts();

        // then
        assertThat(result.getOpenCount()).isEqualTo(2);
        assertThat(result.getClosedCount()).isEqualTo(1);
    }
}
