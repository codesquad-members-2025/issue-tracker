package codesquad.team4.issuetracker.issue;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import codesquad.team4.issuetracker.entity.Issue;
import codesquad.team4.issuetracker.entity.IssueAssignee;
import codesquad.team4.issuetracker.entity.IssueLabel;
import codesquad.team4.issuetracker.issue.dto.IssueRequestDto;
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

@ExtendWith(MockitoExtension.class)
public class IssueServiceTest {

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private IssueLabelRepository issueLabelRepository;

    @Mock
    private IssueAssigneeRepository issueAssigneeRepository;

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

        given(issueRepository.save(Mockito.any(Issue.class))).willReturn(issue);

        // when
        CreateIssueDto response = issueService.createIssue(requestDto, uploadUrl);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getMessage()).isEqualTo("이슈가 생성되었습니다");

        verify(issueRepository, times(1)).save(Mockito.any(Issue.class));
        verify(issueLabelRepository, times(2)).save(Mockito.any(IssueLabel.class));
        verify(issueAssigneeRepository, times(2)).save(Mockito.any(IssueAssignee.class));
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

        given(issueRepository.save(Mockito.any(Issue.class))).willReturn(issue);

        // when
        CreateIssueDto response = issueService.createIssue(requestDto, uploadUrl);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getMessage()).isEqualTo("이슈가 생성되었습니다");

        verify(issueRepository, times(1)).save(Mockito.any(Issue.class));
        verify(issueLabelRepository, times(0)).save(Mockito.any(IssueLabel.class)); // No labels
        verify(issueAssigneeRepository, times(0)).save(Mockito.any(IssueAssignee.class)); // No assignees
    }
}
