package codesquad.team4.issuetracker.issue;

import codesquad.team4.issuetracker.issue.dto.IssueCountDto;
import codesquad.team4.issuetracker.issue.dto.IssueRequestDto;
import codesquad.team4.issuetracker.util.TestDataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
@ActiveProfiles("test")
class IssueServiceH2Test {

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

    @Test
    @DisplayName("상태 변경 성공: 모든 이슈 ID가 유효하면 정상 변경")
    void bulkUpdateIssueStatus_success() {
        // given
        var request = IssueRequestDto.BulkUpdateIssueStatusDto.builder()
                .issuesId(List.of(1L, 2L))
                .isOpen(false)
                .build();

        // when
        var result = issueService.bulkUpdateIssueStatus(request);
        IssueCountDto status = issueService.getIssueCounts();

        // then
        assertThat(result.getIssuesId()).asList().containsExactlyInAnyOrder(1L, 2L);
        assertThat(result.getMessage()).isEqualTo("이슈 상태가 변경되었습니다.");
        assertThat(status.getClosedCount()).isEqualTo(3);
    }

    @Test
    @DisplayName("상태 변경 부분 성공: 일부 ID 존재하지 않음")
    void bulkUpdateIssueStatus_partialSuccess() {
        // given
        var request = IssueRequestDto.BulkUpdateIssueStatusDto.builder()
                .issuesId(List.of(1L, 999L))
                .isOpen(false)
                .build();

        // when
        var result = issueService.bulkUpdateIssueStatus(request);
        IssueCountDto status = issueService.getIssueCounts();

        // then

        // then
        assertThat(result.getIssuesId()).asList().containsExactlyInAnyOrder(1L);
        assertThat(result.getMessage()).contains("일부 이슈 ID는 존재하지 않아 제외되었습니다");
        assertThat(result.getMessage()).contains("999");
        assertThat(status.getClosedCount()).isEqualTo(2);
    }

}
