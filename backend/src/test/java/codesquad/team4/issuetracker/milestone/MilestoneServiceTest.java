package codesquad.team4.issuetracker.milestone;

import codesquad.team4.issuetracker.milestone.dto.MilestoneDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DataJdbcTest
@ActiveProfiles("test")
@Import({MilestoneDao.class, MilestoneService.class})
public class MilestoneServiceTest {
    @Autowired
    private MilestoneService milestoneService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("""
            INSERT INTO milestone (milestone_id, name, description, end_date, is_open)
            VALUES (1, 'week1', 'desc1', CURRENT_DATE, true),
                   (2, 'week2', 'desc2', CURRENT_DATE, false)
        """);
    }
    @Test
    @DisplayName("마일스톤 필터링 정보 조회")
    void 마일스톤_필터링_정보_조회() {
        // when
        MilestoneDto.MilestoneFilter result = milestoneService.getFilterMilestones();

        // then
        assertThat(result.getMilestones()).hasSize(2);
        assertThat(result.getCount()).isEqualTo(2);
        assertThat(result.getMilestones().get(0).getTitle()).isEqualTo("week1");
    }
}
