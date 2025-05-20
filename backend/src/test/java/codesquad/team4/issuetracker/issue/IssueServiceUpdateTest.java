package codesquad.team4.issuetracker.issue;

import codesquad.team4.issuetracker.entity.Issue;
import codesquad.team4.issuetracker.entity.Milestone;
import codesquad.team4.issuetracker.exception.IssueNotFoundException;
import codesquad.team4.issuetracker.exception.MilestoneNotFoundException;
import codesquad.team4.issuetracker.issue.dto.IssueRequestDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import codesquad.team4.issuetracker.label.IssueLabelRepository;
import codesquad.team4.issuetracker.milestone.MilestoneRepository;
import codesquad.team4.issuetracker.user.IssueAssigneeRepository;
import codesquad.team4.issuetracker.util.TestDataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static codesquad.team4.issuetracker.util.TestDataHelper.insertIssueAllParams;
import static codesquad.team4.issuetracker.util.TestDataHelper.insertMilestone;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("test")
class IssueServiceUpdateTest {

    @Autowired
    private IssueService issueService;

    @Autowired
    private IssueDao issueDao;

    @Autowired
    private IssueLabelRepository issueLabelRepository;

    @Autowired
    private IssueAssigneeRepository issueAssigneeRepository;

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private MilestoneRepository milestoneRepository;
    private static final String OLD_IMAGE = "http://s3.com/old.png";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @BeforeEach
    void setUp() {
        TestDataHelper.insertUser(jdbcTemplate, 1L, "사용자1");
        TestDataHelper.insertIssueAllParams(jdbcTemplate, 1L, "이슈 1", true, 1L, "본문", "http://s3.com/old.png", null);
        TestDataHelper.insertMilestone(jdbcTemplate, 1L, "v1.0", "First release", null, true);
    }

    @Test
    @DisplayName("이슈가 정상적으로 수정되어야한다")
    void updateIssueSuccess() {
        //given
        IssueRequestDto.IssueUpdateDto request = IssueRequestDto.IssueUpdateDto.builder()
                .title("Updated title")
                .content("Updated content")
                .milestoneId(1L)
                .isOpen(false)
                .removeImage(false)
                .build();

        //when
        IssueResponseDto.CreateIssueDto result = issueService.updateIssue(1L, request, "");

        Issue updated = issueRepository.findById(1L).get();

        //then
        assertThat(result.getMessage()).isEqualTo("이슈가 수정되었습니다");
        assertThat(updated.getTitle()).isEqualTo("Updated title");
        assertThat(updated.getContent()).isEqualTo("Updated content");
        assertThat(updated.getMilestoneId()).isEqualTo(1L);
        assertThat(updated.isOpen()).isFalse();
    }

    @Test
    @DisplayName("잘못된 이슈 번호를 입력하면 에러가 발생한다")
    void throwWhenIssueIdIsInvalid() {
        //given
        IssueRequestDto.IssueUpdateDto request = IssueRequestDto.IssueUpdateDto.builder().build();

        //when & then
        assertThatThrownBy(() -> issueService.updateIssue(999L, request, ""))
                .isInstanceOf(IssueNotFoundException.class);
    }

    @Test
    @DisplayName("잘못된 마일스톤 번호를 입력하면 에러가 발생한다")
    void throwWhenMilestoneIdIsInvalid() {
        IssueRequestDto.IssueUpdateDto request = IssueRequestDto.IssueUpdateDto.builder()
                .milestoneId(999L)
                .build();

        assertThatThrownBy(() -> issueService.updateIssue(1L, request, ""))
                .isInstanceOf(MilestoneNotFoundException.class);
    }

    @Test
    @DisplayName("removeImage가 true이면 imageUrl이 null이어야한다")
    void removeImageWhenRequested() {
        IssueRequestDto.IssueUpdateDto request = IssueRequestDto.IssueUpdateDto.builder()
                .removeImage(true)
                .build();

        issueService.updateIssue(1L, request, "");

        assertThat(issueRepository.findById(1L).get().getImageUrl()).isNull();
    }

    @Test
    @DisplayName("removeImage가 false면 이전 imageUrl이 유지되어야 한다")
    void keepImageWhenRemoveFalse() {
        IssueRequestDto.IssueUpdateDto request = IssueRequestDto.IssueUpdateDto.builder()
                .removeImage(false)
                .build();

        issueService.updateIssue(1L, request, "");

        assertThat(issueRepository.findById(1L).get().getImageUrl()).isEqualTo(OLD_IMAGE);
    }

    @Test
    @DisplayName("removeImage가 true지만 원래 이미지가 없을 때는 기존 상태를 유지한다")
    void removeImageWhenAlreadyNull() {
        IssueRequestDto.IssueUpdateDto request = IssueRequestDto.IssueUpdateDto.builder()
                .removeImage(true)
                .build();

        issueService.updateIssue(1L, request, "");

        assertThat(issueRepository.findById(1L).get().getImageUrl()).isNull();
    }

}
