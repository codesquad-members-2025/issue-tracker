package codesquad.team4.issuetracker.label;

import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import codesquad.team4.issuetracker.label.dto.LabelDto;
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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@DataJdbcTest
@ActiveProfiles("test")
@Import({LabelDao.class, LabelService.class})
public class LabelServiceTest {

    @Autowired
    private LabelService labelService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("""
            INSERT INTO label (label_id, name, color, description, created_at, updated_at)
            VALUES 
                (1, 'bug', 'qww11', '버그 버그', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
                (2, 'refactor', 'qq2q11', '리팩터링', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
        """);
    }

    @Test
    @DisplayName("레이블 필터링 정보 조회")
    void 레이블_필터링_정보_조회() {
        // when
        LabelDto.LabelFilter result = labelService.getFilterLabels();

        // then
        assertThat(result.getLabels()).hasSize(2);
        assertThat(result.getCount()).isEqualTo(2);

        assertThat(result.getLabels().get(0).getId()).isEqualTo(1L);
        assertThat(result.getLabels().get(0).getName()).isEqualTo("bug");
        assertThat(result.getLabels().get(0).getColor()).isEqualTo("qww11");

        assertThat(result.getLabels().get(1).getId()).isEqualTo(2L);
        assertThat(result.getLabels().get(1).getName()).isEqualTo("refactor");
        assertThat(result.getLabels().get(1).getColor()).isEqualTo("qq2q11");
    }

}
