package CodeSquad.IssueTracker.issue;

import CodeSquad.IssueTracker.comment.CommentService;
import CodeSquad.IssueTracker.issue.dto.IssueUpdateDto;
import CodeSquad.IssueTracker.issueAssignee.IssueAssigneeService;
import CodeSquad.IssueTracker.issueLabel.IssueLabelService;
import CodeSquad.IssueTracker.milestone.MilestoneService;
import CodeSquad.IssueTracker.user.UserService;
import CodeSquad.IssueTracker.util.S3Uploader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class IssueServiceTest {

    private IssueRepository issueRepository;
    private IssueAssigneeService issueAssigneeService;
    private IssueLabelService issueLabelService;
    private UserService userService;
    private CommentService commentService;
    private S3Uploader s3Uploader;
    private MilestoneService milestoneService;
    private IssueService issueService;

    @BeforeEach
    void setUp() {
        issueRepository = mock(IssueRepository.class);
        issueAssigneeService = mock(IssueAssigneeService.class);
        issueLabelService = mock(IssueLabelService.class);
        issueService = new IssueService(issueRepository, issueAssigneeService, issueLabelService
        ,userService,milestoneService,commentService,s3Uploader);
    }

    @Test
    @DisplayName("Issue 저장 시 repository의 save가 호출되고 저장된 결과가 반환된다")
    void saveIssue_callsRepositorySave_andReturnsSavedIssue() {
        // given
        Issue issue = new Issue();
        issue.setTitle("Test Issue");
        when(issueRepository.save(issue)).thenReturn(issue);

        // when
        Issue saved = issueService.save(issue);

        // then
        verify(issueRepository).save(issue);
        assertThat(saved.getTitle()).isEqualTo("Test Issue");
    }

    @Test
    @DisplayName("Issue 업데이트 시 repository의 update가 정상 호출된다")
    void updateIssue_callsRepositoryUpdateCorrectly() {
        // given
        Long issueId = 1L;
        IssueUpdateDto updateDto = new IssueUpdateDto();
        updateDto.setTitle("Updated Title");
        updateDto.setIsOpen(true);
        updateDto.setAssigneeId(3L);
        updateDto.setMilestoneId(2L);
        updateDto.setTimestamp(LocalDateTime.now());

        // when
        issueService.update(issueId, updateDto);

        // then
        verify(issueRepository).update(issueId, updateDto);
    }

    @Test
    @DisplayName("ID로 Issue 조회 시 정상적으로 조회된다")
    void findIssueById_returnsCorrectIssue() {
        // given
        Long issueId = 1L;
        Issue issue = new Issue();
        issue.setIssueId(issueId);
        issue.setTitle("조회용 이슈");
        when(issueRepository.findById(issueId)).thenReturn(Optional.of(issue));

        // when
        Optional<Issue> result = issueService.findById(issueId);

        // then
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("조회용 이슈");
    }

    @Test
    @DisplayName("모든 Issue를 조회하면 전체 목록이 반환된다")
    void findAllIssues_returnsAllIssues() {
        // given
        Issue i1 = new Issue(); i1.setTitle("Issue1");
        Issue i2 = new Issue(); i2.setTitle("Issue2");
        when(issueRepository.findAll()).thenReturn(List.of(i1, i2));

        // when
        Iterable<Issue> all = issueService.findAll();

        // then
        assertThat(all).hasSize(2);
    }
}
