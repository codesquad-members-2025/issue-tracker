package codesquad.team4.issuetracker.label;

import codesquad.team4.issuetracker.label.dto.LabelCountDto;
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
public class LabelServiceTest {

    @Mock
    JdbcTemplate jdbcTemplate;
    @InjectMocks
    LabelService labelService; //todo 서비스를 테스트하는건데 서비스가 Mock이면 검사가 안됨

    @Test
    @DisplayName("생성된 레이블 개수 반환")
    void 레이블_개수_조회() {
        given(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM label", Integer.class))
                .willReturn(8);

        LabelCountDto result = labelService.getLabelCount();

        assertThat(result.getCount()).isEqualTo(8);
    }

}
