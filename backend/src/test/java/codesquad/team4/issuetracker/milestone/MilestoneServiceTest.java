package codesquad.team4.issuetracker.milestone;

import codesquad.team4.issuetracker.milestone.dto.MilestoneCountDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MilestoneServiceTest {
    @Mock
    JdbcTemplate jdbcTemplate;
    @InjectMocks
    MilestoneService milestoneService;

    @Test
    @DisplayName("생성된 마일스톤 개수 반환")
    void 마일스톤_개수_반환() {
        given(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM milestone", Integer.class))
                .willReturn(8);

        MilestoneCountDto result = milestoneService.getMilestoneCount();

        assertThat(result.getCount()).isEqualTo(8);
    }
}
