package codesquad.team4.issuetracker.issue;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import codesquad.team4.issuetracker.entity.Issue;
import codesquad.team4.issuetracker.entity.IssueAssignee;
import codesquad.team4.issuetracker.entity.IssueLabel;
import codesquad.team4.issuetracker.exception.IssueStatusUpdateException;
import codesquad.team4.issuetracker.issue.dto.IssueRequestDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto.CreateIssueDto;
import codesquad.team4.issuetracker.label.IssueLabelRepository;
import codesquad.team4.issuetracker.user.IssueAssigneeRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

@ExtendWith(MockitoExtension.class)
public class IssueServiceTest {

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private IssueLabelRepository issueLabelRepository;

    @Mock
    private IssueAssigneeRepository issueAssigneeRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private IssueService issueService;

    private IssueRequestDto.CreateIssueDto.CreateIssueDtoBuilder requestDtoBuilder;

    @BeforeEach
    public void setup() {
        requestDtoBuilder = IssueRequestDto.CreateIssueDto.builder()
                .title("Test Issue")
                .content("Test Content")
                .authorId(1L)
                .milestoneId(null)
                .labelId(List.of(1L, 2L))
                .assigneeId(List.of(1L, 2L));
    }

    @Test
    @DisplayName("새 이슈를 생성할 때, 정상적으로 이슈와 관련된 레이블과 담당자가 저장되어야 한다")
    public void testCreateIssue_Success() {
        // given
        IssueRequestDto.CreateIssueDto requestDto = requestDtoBuilder.build();
        String uploadUrl = "http://example.com/uploaded_image.jpg";

        Issue issue = Issue.builder()
                .id(1L)
                .title("Test Issue")
                .content("Test Content")
                .imageUrl(uploadUrl)
                .isOpen(true)
                .authorId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(issueRepository.save(any(Issue.class))).willReturn(issue);

        // when
        CreateIssueDto response = issueService.createIssue(requestDto, uploadUrl);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getMessage()).isEqualTo("이슈가 생성되었습니다");

        verify(issueRepository, times(1)).save(any(Issue.class));
        verify(issueLabelRepository, times(2)).save(any(IssueLabel.class));
        verify(issueAssigneeRepository, times(2)).save(any(IssueAssignee.class));
    }

    @Test
    @DisplayName("새 이슈를 생성할 때, 레이블과 담당자가 없어도 저장되어야 한다")
    public void testCreateIssue_NoLabelsAndAssignees() {
        // given
        IssueRequestDto.CreateIssueDto requestDto = requestDtoBuilder
                .labelId(null)
                .assigneeId(null)
                .build();
        String uploadUrl = "http://example.com/uploaded_image.jpg";

        Issue issue = Issue.builder()
                .id(1L)
                .title("Test Issue")
                .content("Test Content")
                .imageUrl(uploadUrl)
                .isOpen(true)
                .authorId(1L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        given(issueRepository.save(any(Issue.class))).willReturn(issue);

        // when
        CreateIssueDto response = issueService.createIssue(requestDto, uploadUrl);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getMessage()).isEqualTo("이슈가 생성되었습니다");

        verify(issueRepository, times(1)).save(any(Issue.class));
        verify(issueLabelRepository, times(0)).save(any(IssueLabel.class)); // No labels
        verify(issueAssigneeRepository, times(0)).save(any(IssueAssignee.class)); // No assignees
    }

    @Test
    @DisplayName("상태 변경 성공: 모든 이슈 ID가 유효하면 상태가 정상적으로 변경된다")
    public void testUpdateIssueStatus_Success() {
        // given
        List<Long> issuesId = List.of(1L, 2L, 3L);
        boolean isOpen = true;

        IssueRequestDto.BulkUpdateIssueStatusDto requestDto = IssueRequestDto.BulkUpdateIssueStatusDto.builder()
                .issuesId(issuesId)
                .isOpen(isOpen)
                .build();

        String placeholders = "?, ?, ?";
        String countSql = "SELECT COUNT(*) FROM issue WHERE issue_id IN (" + placeholders + ")";
        String updateSql = "UPDATE issue SET is_open = ? WHERE issue_id IN (" + placeholders + ")";

        Object[] queryArgs = new Object[]{1L, 2L, 3L};
        Object[] updateArgs = new Object[]{isOpen, 1L, 2L, 3L};

        given(jdbcTemplate.queryForObject(eq(countSql), eq(Integer.class), eq(queryArgs)))
                .willReturn(3);
        given(jdbcTemplate.update(eq(updateSql), eq(updateArgs)))
                .willReturn(3);

        // when
        IssueResponseDto.BulkUpdateIssueStatusDto result = issueService.bulkUpdateIssueStatus(requestDto);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getIssuesId()).isEqualTo(issuesId);
        assertThat(result.getMessage()).isEqualTo("이슈 상태가 변경되었습니다.");
    }

    @Test
    @DisplayName("상태 변경 실패: 일부 이슈 ID만 존재하면 예외가 발생한다")
    public void testUpdateIssueStatus_InvalidIds_ThrowsException() {
        // given
        List<Long> issuesId = List.of(1L, 2L, 3L);
        boolean isOpen = false;

        IssueRequestDto.BulkUpdateIssueStatusDto requestDto = IssueRequestDto.BulkUpdateIssueStatusDto.builder()
                .issuesId(issuesId)
                .isOpen(isOpen)
                .build();

        String placeholders = "?, ?, ?";
        String countSql = "SELECT COUNT(*) FROM issue WHERE issue_id IN (" + placeholders + ")";
        Object[] queryArgs = new Object[]{1L, 2L, 3L};

        // 일부 ID만 존재한다고 가정 (ex. 2개)
        given(jdbcTemplate.queryForObject(eq(countSql), eq(Integer.class), eq(queryArgs)))
                .willReturn(2);

        // when & then
        assertThatThrownBy(() -> issueService.bulkUpdateIssueStatus(requestDto))
                .isInstanceOf(IssueStatusUpdateException.class)
                .hasMessageContaining("일부");
    }


}
