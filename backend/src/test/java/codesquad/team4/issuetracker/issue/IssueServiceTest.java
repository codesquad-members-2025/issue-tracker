package codesquad.team4.issuetracker.issue;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import codesquad.team4.issuetracker.comment.dto.CommentResponseDto;
import codesquad.team4.issuetracker.entity.Issue;
import codesquad.team4.issuetracker.entity.IssueAssignee;
import codesquad.team4.issuetracker.entity.IssueLabel;
import codesquad.team4.issuetracker.exception.IssueNotFoundException;
import codesquad.team4.issuetracker.issue.dto.IssueRequestDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto.ApiMessageDto;
import codesquad.team4.issuetracker.label.IssueLabelRepository;
import codesquad.team4.issuetracker.user.IssueAssigneeRepository;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class IssueServiceTest {

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private IssueLabelRepository issueLabelRepository;

    @Mock
    private IssueAssigneeRepository issueAssigneeRepository;

    @Mock
    private IssueDao issueDao;

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
                .labelId(Set.of(1L, 2L))
                .assigneeId(Set.of(1L, 2L));
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
        ApiMessageDto response = issueService.createIssue(requestDto, uploadUrl);

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
        ApiMessageDto response = issueService.createIssue(requestDto, uploadUrl);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getMessage()).isEqualTo("이슈가 생성되었습니다");

        verify(issueRepository, times(1)).save(any(Issue.class));
        verify(issueLabelRepository, times(0)).save(any(IssueLabel.class)); // No labels
        verify(issueAssigneeRepository, times(0)).save(any(IssueAssignee.class)); // No assignees
    }

    @Test
    @DisplayName("이슈 상세 정보를 성공적으로 조회한다")
    void successGetIssueDetailById() {
        // given
        Long issueId = 1L;
        Map<String, Object> row = new HashMap<>();
        row.put("issue_content", "이슈 내용");
        row.put("issue_image_url", "https://example.com/image.png");
        row.put("comment_id", 10L);
        row.put("comment_content", "댓글 내용");
        row.put("comment_image_url", "https://example.com/comment.png");
        row.put("comment_created_at", Timestamp.valueOf(LocalDateTime.now()));
        row.put("author_id", 2L);
        row.put("author_nickname", "작성자");
        row.put("author_profile", "https://example.com/profile.png");

        List<Map<String, Object>> mockResult = List.of(row);
        given(issueDao.findIssueDetailById(issueId)).willReturn(mockResult);

        // when
        IssueResponseDto.searchIssueDetailDto result = issueService.getIssueDetailById(issueId);

        // then
        assertThat(result.getContent()).isEqualTo("이슈 내용");
        assertThat(result.getContentImageUrl()).isEqualTo("https://example.com/image.png");
        assertThat(result.getComments()).hasSize(1);

        CommentResponseDto.CommentInfo comment = result.getComments().get(0);
        assertThat(comment.getCommentId()).isEqualTo(10L);
        assertThat(comment.getContent()).isEqualTo("댓글 내용");
        assertThat(comment.getAuthor().getNickname()).isEqualTo("작성자");
    }

    @Test
    @DisplayName("이슈가 존재하지 않으면 예외를 던진다")
    void getIssueDetailByNotExistId() {
        // given
        Long issueId = 999L;
        given(issueDao.findIssueDetailById(issueId)).willReturn(Collections.emptyList());

        // when & then
        assertThatThrownBy(() -> issueService.getIssueDetailById(issueId))
                .isInstanceOf(IssueNotFoundException.class)
                .hasMessageContaining("이슈를 찾을 수 없습니다. issueId = " + issueId);
    }
}
