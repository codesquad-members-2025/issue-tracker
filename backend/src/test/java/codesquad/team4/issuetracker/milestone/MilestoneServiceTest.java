package codesquad.team4.issuetracker.milestone;

import codesquad.team4.issuetracker.count.CountService;
import codesquad.team4.issuetracker.count.dto.MilestoneCountDto;
import codesquad.team4.issuetracker.entity.Milestone;
import codesquad.team4.issuetracker.exception.notfound.MilestoneNotFoundException;
import codesquad.team4.issuetracker.milestone.dto.MilestoneRequestDto;
import codesquad.team4.issuetracker.milestone.dto.MilestoneResponseDto;
import codesquad.team4.issuetracker.util.TestDataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
public class MilestoneServiceTest {
    @Autowired
    private MilestoneService milestoneService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private CountService countService;

    @BeforeEach
    void setUp() {
        TestDataHelper.insertMilestone(jdbcTemplate, 1L, "week1", "1주차", LocalDate.of(2025, 6, 25), true);
        TestDataHelper.insertMilestone(jdbcTemplate, 2L, "week2", "2주차", LocalDate.of(2025, 7, 25), false);

        // 마일스톤 1에 연결된 이슈 삽입 (1 open, 1 closed)
        TestDataHelper.insertUser(jdbcTemplate, 1L, "user1");
        TestDataHelper.insertIssueAllParams(jdbcTemplate, 1L, "issue1", true, 1L, "1111", null, 1L);
        TestDataHelper.insertIssueAllParams(jdbcTemplate, 2L, "issue2", false, 1L, "2222", null, 1L);
        Map<String, Integer> summaryMap = Map.of(
                "issue_open", 1,
                "issue_closed", 1,
                "milestone_open", 1,
                "milestone_closed", 1
        );
        TestDataHelper.insertSummaryCount(jdbcTemplate, summaryMap);
    }
    @Test
    @DisplayName("마일스톤 필터링 정보 조회")
    void 마일스톤_필터링_정보_조회() {
        // when
        MilestoneResponseDto.MilestoneFilter result = milestoneService.getFilterMilestones();

        // then
        assertThat(result.getMilestones()).hasSize(2);
        assertThat(result.getCount()).isEqualTo(2);
        assertThat(result.getMilestones().get(0).getTitle()).isEqualTo("week1");
    }

    @Test
    @DisplayName("마일스톤 생성 성공")
    void createMilestone_success() {
        MilestoneRequestDto.CreateMilestoneDto request = new MilestoneRequestDto.CreateMilestoneDto(
            "week3", "3333", LocalDate.of(2025, 8, 25)
        );

        // when
        milestoneService.createMilestone(request);

        // then
        List<Milestone> milestones = new ArrayList<>();
        milestoneRepository.findAll().forEach(milestones::add);

        assertThat(milestones).hasSize(3);
        assertThat(milestones).extracting("name").contains("week3"); // 방금 만든 이름이 포함되는지
    }

    @Test
    @DisplayName("마일스톤 목록 조회 - 열린")
    void findMilestone_open() {
        MilestoneResponseDto.MilestoneListDto result = milestoneService.getMilestones(true);

        assertThat(result.getMilestones()).hasSize(1);
        var milestone = result.getMilestones().get(0);
        assertThat(milestone.getName()).isEqualTo("week1");
        assertThat(milestone.getOpenIssues()).isEqualTo(1);
        assertThat(milestone.getClosedIssues()).isEqualTo(1);
        assertThat(milestone.getProgress()).isEqualTo(50);
    }

    @Test
    @DisplayName("마일스톤 목록 조회 - 닫힌")
    void findMilestone_closed() {
        MilestoneResponseDto.MilestoneListDto result = milestoneService.getMilestones(false);

        assertThat(result.getMilestones()).hasSize(1);
        assertThat(result.getMilestones().get(0).getName()).isEqualTo("week2");
    }

    @Test
    @DisplayName("마일스톤 수정 성공")
    void updateMilestone_success() {
        MilestoneRequestDto.CreateMilestoneDto request = new MilestoneRequestDto.CreateMilestoneDto(
            "week2-수정", "2222", LocalDate.of(2025, 7, 20)
        );

        milestoneService.updateMilestone(1L, request);

        Milestone updated = milestoneRepository.findById(1L).orElseThrow();
        assertThat(updated.getName()).isEqualTo("week2-수정");
        assertThat(updated.getDescription()).isEqualTo("2222");
    }

    @Test
    @DisplayName("마일스톤 수정 실패 - 존재하지 않음")
    void updateMilestone_fail_notFound() {
        MilestoneRequestDto.CreateMilestoneDto request = new MilestoneRequestDto.CreateMilestoneDto(
            "week2-수정", "2222", LocalDate.of(2025, 7, 20)
        );

        assertThatThrownBy(() -> milestoneService.updateMilestone(999L, request))
            .isInstanceOf(MilestoneNotFoundException.class);

    }

    @Test
    @DisplayName("마일스톤 개수 조회")
    void countMilestone_closed() {
        MilestoneCountDto result = countService.getMilestoneCounts();
        assertThat(result.getOpenCount()).isEqualTo(1);
        assertThat(result.getClosedCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("마일스톤 닫기 성공")
    void closeMilestone_success() {
        milestoneService.closeMilestone(1L);

        Milestone updated = milestoneRepository.findById(1L).orElseThrow();
        assertThat(updated.isOpen()).isFalse();
    }

    @Test
    @DisplayName("마일스톤 닫기 실패 - 존재하지 않음")
    void closeMilestone_fail_notFound() {
        assertThatThrownBy(() -> milestoneService.closeMilestone(999L))
            .isInstanceOf(MilestoneNotFoundException.class);
    }

    @Test
    @DisplayName("마일스톤 삭제 성공")
    void deleteMilestone_success() {
        milestoneService.deleteMilestone(2L);

        assertThat(milestoneRepository.existsById(2L)).isFalse();
    }
}
