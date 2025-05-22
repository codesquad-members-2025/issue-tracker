package codesquad.team4.issuetracker.issue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import codesquad.team4.issuetracker.exception.notfound.IssueNotFoundException;
import codesquad.team4.issuetracker.issue.dto.IssueCountDto;
import codesquad.team4.issuetracker.issue.dto.IssueRequestDto;
import codesquad.team4.issuetracker.issue.dto.IssueRequestDto.IssueFilterParamDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto.IssueInfo;
import codesquad.team4.issuetracker.util.TestDataHelper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

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

    @Test
    @DisplayName("이슈를 조회할때 페이징 정보와 이슈 정보를 가져온다")
    void issueListPageInfoTest() {
        //given
        TestDataHelper.insertUser(jdbcTemplate, 2L, "사용자2");
        TestDataHelper.insertLabel(jdbcTemplate, 1L, "라벨1", "color");
        TestDataHelper.insertLabel(jdbcTemplate, 2L, "라벨2", "color");
        TestDataHelper.insertIssueLabel(jdbcTemplate, 1L, 1L, 1L);
        TestDataHelper.insertIssueLabel(jdbcTemplate, 2L, 2L, 1L);
        TestDataHelper.insertIssueLabel(jdbcTemplate, 3L, 2L, 2L);
        TestDataHelper.insertIssueAssignee(jdbcTemplate, 1L, 1L, 1L);
        TestDataHelper.insertIssueAssignee(jdbcTemplate, 2L, 1L, 2L);
        TestDataHelper.insertIssueAssignee(jdbcTemplate, 3L, 2L, 1L);

        IssueFilterParamDto param = IssueFilterParamDto.builder()
            .isOpen(true)
            .build();
        //when
        IssueResponseDto.IssueListDto actual = issueService.getIssues(param, 0, 2);

        //then
        assertThat(actual.getIssues()).hasSize(2);
        assertThat(actual.getIssues().get(0).getLabels()).hasSize(1);
        assertThat(actual.getIssues().get(0).getAssignees()).hasSize(2);
        assertThat(actual.getIssues().get(1).getLabels()).hasSize(2);
        assertThat(actual.getIssues().get(1).getAssignees()).hasSize(1);
        assertThat(actual.getPage()).isEqualTo(0);
        assertThat(actual.getSize()).isEqualTo(2);
    }

    @Test
    @DisplayName("존재하지 않는 이슈를 삭제하려 하면 에러가 발생한다")
    void deleteNotExistIssue() {
        //given
        Long issueId = 999L;

        //when & then
        assertThatThrownBy(() -> issueService.deleteIssue(issueId))
                .isInstanceOf(IssueNotFoundException.class);
    }

    @ParameterizedTest
    @DisplayName("필터링 조건이 내가 작성한 이슈를 가져오는 경우 다른 사람이 작성한 이슈는 가져와서는 안된다")
    @CsvSource({
                "true, 3, 1",
                "false, 1, 1",
                "true, 1, 2",
                "false, 1, 2"})
    void filterIssueByAuthor(boolean isOpen, int expectedSize, Long authorId) {
        //given
        TestDataHelper.insertUser(jdbcTemplate, 2L, "사용자2");
        TestDataHelper.insertIssue(jdbcTemplate, 4L, "이슈 4", true, 1L);   // open
        TestDataHelper.insertIssue(jdbcTemplate, 5L, "이슈 5", true, 2L);   // open
        TestDataHelper.insertIssue(jdbcTemplate, 6L, "이슈 6", false, 2L);  // closed

        IssueFilterParamDto param = IssueFilterParamDto.builder()
            .isOpen(isOpen)
            .authorId(authorId)
            .build();
        //when
        IssueResponseDto.IssueListDto issues = issueService.getIssues(param, 0, 10);

        //then
        assertThat(issues.getIssues()).hasSize(expectedSize);
        assertThat(issues.getIssues())
            .allSatisfy(issue -> assertThat(issue.getAuthor().getId()).isEqualTo(authorId));
    }

    @ParameterizedTest
    @DisplayName("필터링 조건이 내가 담당한 이슈를 가져오는 경우 내가 담당하지 않은 이슈는 가져와서는 안된다")
    @CsvSource({
        "true, 1, 1",
        "false, 0, 1",
        "true, 1, 2",
        "false, 1, 2",
        "true, 2, 3",
        "false, 0, 3"})
    void filterIssueByAssignee(boolean isOpen, int expectedSize, Long assigneeId) {
        //given
        TestDataHelper.insertUser(jdbcTemplate, 2L, "사용자2");
        TestDataHelper.insertUser(jdbcTemplate, 3L, "사용자3");

        TestDataHelper.insertIssue(jdbcTemplate, 4L, "이슈 4", true, 1L);   // open
        TestDataHelper.insertIssue(jdbcTemplate, 5L, "이슈 5", true, 2L);   // open
        TestDataHelper.insertIssue(jdbcTemplate, 6L, "이슈 6", false, 2L);  // closed

        //담당자 설정
        TestDataHelper.insertIssueAssignee(jdbcTemplate, 1L, 1L, 1L);
        TestDataHelper.insertIssueAssignee(jdbcTemplate, 2L, 1L, 2L);
        TestDataHelper.insertIssueAssignee(jdbcTemplate, 3L, 3L, 2L);
        TestDataHelper.insertIssueAssignee(jdbcTemplate, 4L, 1L, 3L);
        TestDataHelper.insertIssueAssignee(jdbcTemplate, 5L, 4L, 3L);

        IssueFilterParamDto param = IssueFilterParamDto.builder()
            .isOpen(isOpen)
            .assigneeId(assigneeId)
            .build();
        //when
        IssueResponseDto.IssueListDto issues = issueService.getIssues(param, 0, 10);

        //then
        assertThat(issues.getIssues()).hasSize(expectedSize);
        assertThat(issues.getIssues())
            .allSatisfy(issue ->
                assertThat(issue.getAssignees().stream()
                    .anyMatch(assignee -> assignee.getId().equals(assigneeId)))
                    .isTrue()
            );
    }

    @ParameterizedTest
    @DisplayName("필터링 조건이 내가 댓글을 작성한 이슈를 가져오는 경우 댓글을 달지 않은 이슈는 가져와서는 안된다")
    @CsvSource({
        "true, 1, 1",
        "false, 0, 1",
        "true, 1, 2",
        "false, 1, 2",
        "true, 0, 3",
        "false, 2, 3"
    })
    void filterIssueByCommentAuthor(boolean isOpen, int expectedSize, Long commentAuthorId) {
        // given
        TestDataHelper.insertUser(jdbcTemplate, 2L, "사용자2");
        TestDataHelper.insertUser(jdbcTemplate, 3L, "사용자3");

        TestDataHelper.insertIssue(jdbcTemplate, 4L, "이슈 4", true, 1L);   // open
        TestDataHelper.insertIssue(jdbcTemplate, 5L, "이슈 5", true, 2L);   // open
        TestDataHelper.insertIssue(jdbcTemplate, 6L, "이슈 6", false, 2L);  // closed

        // 댓글 작성
        TestDataHelper.insertComment(jdbcTemplate, 1L, "댓글1", 1L, 1L, null);
        TestDataHelper.insertComment(jdbcTemplate, 2L, "댓글2", 2L, 2L, null);
        TestDataHelper.insertComment(jdbcTemplate, 3L, "댓글3", 2L, 3L, null);
        TestDataHelper.insertComment(jdbcTemplate, 4L, "댓글4", 3L, 3L, null);
        TestDataHelper.insertComment(jdbcTemplate, 5L, "댓글5", 3L, 6L, null);

        IssueFilterParamDto param = IssueFilterParamDto.builder()
            .isOpen(isOpen)
            .commentAuthorId(commentAuthorId)
            .build();

        // when
        IssueResponseDto.IssueListDto issues = issueService.getIssues(param, 0, 10);

        //필터링한 이슈 아이디와 DB에서 조회한 이슈 아이디가 일치하는지 확인
        List<Long> actualIssueIds = issues.getIssues().stream()
            .map(IssueInfo::getId)
            .toList();

        String sql = """
            SELECT DISTINCT c.issue_id
            FROM comment c
            JOIN issue i ON c.issue_id = i.issue_id
            WHERE c.author_id = ?
            AND i.is_open = ?
        """;

        List<Long> expectedIssueIds = jdbcTemplate.queryForList(sql, Long.class, commentAuthorId, isOpen);

        // then
        assertThat(issues.getIssues()).hasSize(expectedSize);
        assertThat(actualIssueIds).containsExactlyInAnyOrderElementsOf(expectedIssueIds);
    }
}
