package codesquad.team4.issuetracker.label;

import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import codesquad.team4.issuetracker.label.dto.LabelFilterDto;
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
public class LabelServiceTest {

    @Mock
    JdbcTemplate jdbcTemplate;
    @InjectMocks
    LabelService labelService;

    @Test
    @DisplayName("레이블 필터링 정보 조회")
    void 레이블_필터링_정보_조회() {
        // given
        String sql = "SELECT label_id, name, color FROM label";

        List<IssueResponseDto.LabelInfo> mockLabels = List.of(
                IssueResponseDto.LabelInfo.builder()
                        .id(1L)
                        .name("bug")
                        .color("qww11")
                        .build(),
                IssueResponseDto.LabelInfo.builder()
                        .id(2L)
                        .name("refactor")
                        .color("qq2q11")
                        .build()
        );

        given(jdbcTemplate.query(eq(sql), any(RowMapper.class))).willReturn(mockLabels);

        // when
        LabelFilterDto result = labelService.getFilterLabels();

        // then
        assertThat(result.getLabels()).hasSize(2);
        assertThat(result.getCount()).isEqualTo(2);
        assertThat(result.getLabels().get(0).getId()).isEqualTo(1L);
        assertThat(result.getLabels().get(0).getName()).isEqualTo("bug");
        assertThat(result.getLabels().get(0).getColor()).isEqualTo("qww11");
    }

}
