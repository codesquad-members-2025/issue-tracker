package codesquad.team4.issuetracker.milestone;

import codesquad.team4.issuetracker.milestone.dto.MilestoneDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MilestoneServiceTest {
    @Mock
    JdbcTemplate jdbcTemplate;
    @InjectMocks
    MilestoneService milestoneService;

    @Test
    @DisplayName("마일스톤 필터링 정보 조회")
    void 마일스톤_필터링_정보_조회() {
        // given
        String sql = "SELECT milestone_id, name FROM milestone";

        List<MilestoneDto.MilestoneInfo> mockMilestones = List.of(
                MilestoneDto.MilestoneInfo.builder()
                        .id(1L)
                        .name("week1")
                        .build(),
                MilestoneDto.MilestoneInfo.builder()
                        .id(2L)
                        .name("week2")
                        .build()
        );

        given(jdbcTemplate.query(eq(sql), any(RowMapper.class))).willReturn(mockMilestones);

        // when
        MilestoneDto.MilestoneFilter result = milestoneService.getFilterMilestones();

        // then
        assertThat(result.getMilestones()).hasSize(2);
        assertThat(result.getCount()).isEqualTo(2);
        assertThat(result.getMilestones().get(0).getId()).isEqualTo(1L);
        assertThat(result.getMilestones().get(0).getName()).isEqualTo("week1");
    }
}
