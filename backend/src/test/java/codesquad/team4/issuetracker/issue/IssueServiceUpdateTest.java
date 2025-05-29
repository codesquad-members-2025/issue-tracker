package codesquad.team4.issuetracker.issue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import codesquad.team4.issuetracker.entity.Issue;
import codesquad.team4.issuetracker.entity.IssueAssignee;
import codesquad.team4.issuetracker.entity.IssueLabel;
import codesquad.team4.issuetracker.exception.notfound.AssigneeNotFoundException;
import codesquad.team4.issuetracker.exception.notfound.IssueNotFoundException;
import codesquad.team4.issuetracker.exception.notfound.LabelNotFoundException;
import codesquad.team4.issuetracker.exception.notfound.MilestoneNotFoundException;
import codesquad.team4.issuetracker.issue.dto.IssueRequestDto;
import codesquad.team4.issuetracker.issue.dto.IssueResponseDto.ApiMessageDto;
import codesquad.team4.issuetracker.label.IssueLabelRepository;
import codesquad.team4.issuetracker.milestone.MilestoneRepository;
import codesquad.team4.issuetracker.user.IssueAssigneeRepository;
import codesquad.team4.issuetracker.util.TestDataHelper;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
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
        TestDataHelper.insertUser(jdbcTemplate, 2L, "사용자2");
        TestDataHelper.insertUser(jdbcTemplate, 3L, "사용자3");

        TestDataHelper.insertIssueAllParams(jdbcTemplate, 1L, "이슈 1", true, 1L, "본문", "http://s3.com/old.png", null);
        TestDataHelper.insertMilestone(jdbcTemplate, 1L, "v1.0", "First release", null, true);

        TestDataHelper.insertLabel(jdbcTemplate, 1L, "bug", "#ff0000");
        TestDataHelper.insertLabel(jdbcTemplate, 2L, "feature", "#00ff00");
        TestDataHelper.insertLabel(jdbcTemplate, 3L, "urgent", "#0000ff");
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
        ApiMessageDto result = issueService.updateIssue(1L, request, "");

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
    @DisplayName("removeImage가 true이면 fileUrl이 빈 문자열이어야한다")
    void removeImageWhenRequested() {
        IssueRequestDto.IssueUpdateDto request = IssueRequestDto.IssueUpdateDto.builder()
                .removeImage(true)
                .build();

        issueService.updateIssue(1L, request, "");

        assertThat(issueRepository.findById(1L).get().getFileUrl()).isEmpty();
    }

    @Test
    @DisplayName("removeImage가 false면 이전 fileUrl이 유지되어야 한다")
    void keepImageWhenRemoveFalse() {
        IssueRequestDto.IssueUpdateDto request = IssueRequestDto.IssueUpdateDto.builder()
                .removeImage(false)
                .build();

        issueService.updateIssue(1L, request, "");

        assertThat(issueRepository.findById(1L).get().getFileUrl()).isEqualTo(OLD_IMAGE);
    }

    @Test
    @DisplayName("removeImage가 true지만 원래 이미지가 없을 때는 기존 상태를 유지한다")
    void removeImageWhenAlreadyNull() {
        IssueRequestDto.IssueUpdateDto request = IssueRequestDto.IssueUpdateDto.builder()
                .removeImage(true)
                .build();

        issueService.updateIssue(1L, request, "");

        assertThat(issueRepository.findById(1L).get().getFileUrl()).isEmpty();
    }


    @Test
    @DisplayName("정상적으로 레이블 업데이트가 수행된다")
    void updateLabels_success() {
        //given
        Set<Long> labelIds = Set.of(1L, 2L, 3L);

        //when
        ApiMessageDto result = issueService.updateLabels(1L, labelIds);

        //then
        assertThat(result.getMessage()).isEqualTo("이슈의 레이블이 수정되었습니다");

        List<IssueLabel> mappings = issueLabelRepository.findAllByIssueId(1L);
        assertThat(mappings).hasSize(labelIds.size());
    }

    @Test
    @DisplayName("이슈 아이디가 존재하지 않으면 에러가 발생한다")
    void updateLabels_issueNotFound() {
        //given
        Long nonExistentIssueId = 9999L;
        Set<Long> labelIds = Set.of(1L, 2L, 3L);

        //when & then
        assertThatThrownBy(() -> issueService.updateLabels(nonExistentIssueId, labelIds))
                .isInstanceOf(IssueNotFoundException.class);
    }

    @Test
    @DisplayName("존재하지 않는 레이블로 수정하려하면 에러가 발생한다")
    void updateLabels_labelNotFound() {
        //given
        Set<Long> invalidLabelIds = Set.of(100L, 200L);

        //when & then
        assertThatThrownBy(() -> issueService.updateLabels(1L, invalidLabelIds))
                .isInstanceOf(LabelNotFoundException.class);
    }

    @Test
    @DisplayName("빈 레이블 리스트가 온다면 삭제 후 아무것도 추가하지 않는다")
    void updateLabels_withEmptyLabelList() {
        //given
        issueService.updateLabels(1L, Set.of());

        //when
        List<IssueLabel> mappings = issueLabelRepository.findAllByIssueId(1L);

        //then
        assertThat(mappings.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("정상적으로 담당자가 업데이트된다")
    void updateAssignees_success() {
        // given
        Set<Long> assigneeIds = Set.of(1L, 2L, 3L);

        // when
        ApiMessageDto result = issueService.updateAssignees(1L, assigneeIds);

        // then
        assertThat(result.getMessage()).isEqualTo("이슈의 담당자가 수정되었습니다");

        List<IssueAssignee> mappings = issueAssigneeRepository.findAllByIssueId(1L);
        assertThat(mappings).hasSize(assigneeIds.size());
    }

    @Test
    @DisplayName("이슈 ID가 존재하지 않으면 예외가 발생한다")
    void updateAssignees_issueNotFound() {
        // given
        Long nonExistentIssueId = 9999L;
        Set<Long> assigneeIds = Set.of(1L, 2L);

        // when & then
        assertThatThrownBy(() -> issueService.updateAssignees(nonExistentIssueId, assigneeIds))
                .isInstanceOf(IssueNotFoundException.class);
    }

    @Test
    @DisplayName("존재하지 않는 유저를 담당자로 지정하면 예외가 발생한다")
    void updateAssignees_assigneeNotFound() {
        // given
        Set<Long> invalidUserIds = Set.of(100L, 200L);

        // when & then
        assertThatThrownBy(() -> issueService.updateAssignees(1L, invalidUserIds))
                .isInstanceOf(AssigneeNotFoundException.class);
    }

    @Test
    @DisplayName("빈 담당자 목록이 오면 기존 매핑은 삭제되고 아무것도 추가되지 않는다")
    void updateAssignees_withEmptyAssigneeList() {
        // when
        issueService.updateAssignees(1L, Set.of());

        // then
        List<IssueAssignee> mappings = issueAssigneeRepository.findAllByIssueId(1L);
        assertThat(mappings).isEmpty();
    }
}
