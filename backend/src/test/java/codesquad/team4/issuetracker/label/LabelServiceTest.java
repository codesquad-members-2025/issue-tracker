package codesquad.team4.issuetracker.label;

import codesquad.team4.issuetracker.entity.Label;
import codesquad.team4.issuetracker.entity.Milestone;
import codesquad.team4.issuetracker.exception.notfound.LabelNotFoundException;
import codesquad.team4.issuetracker.label.dto.LabelRequestDto;
import codesquad.team4.issuetracker.label.dto.LabelResponseDto;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
public class LabelServiceTest {

    @Autowired
    private LabelService labelService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LabelRepository labelRepository;

    @BeforeEach
    void setUp() {
        TestDataHelper.insertLabel(jdbcTemplate, 1L, "bug", "#ff0000");
        TestDataHelper.insertLabel(jdbcTemplate, 2L, "feature", "#00ff00");
    }

    @Test
    @DisplayName("레이블 필터링 정보 조회")
    void 레이블_필터링_정보_조회() {
        // when
        LabelResponseDto.LabelFilter result = labelService.getFilterLabels();

        // then
        assertThat(result.getLabels()).hasSize(2);
        assertThat(result.getCount()).isEqualTo(2);

        assertThat(result.getLabels().get(0).getId()).isEqualTo(1L);
        assertThat(result.getLabels().get(0).getName()).isEqualTo("bug");
        assertThat(result.getLabels().get(0).getColor()).isEqualTo("#ff0000");

        assertThat(result.getLabels().get(1).getId()).isEqualTo(2L);
        assertThat(result.getLabels().get(1).getName()).isEqualTo("feature");
        assertThat(result.getLabels().get(1).getColor()).isEqualTo("#00ff00");
    }

    @Test
    @DisplayName("레이블 목록 조회")
    void getAllLabels() {
        LabelResponseDto.LabelListDto result = labelService.getAllLabels();

        assertThat(result.getLabels()).hasSize(2);
    }

    @Test
    @DisplayName("레이블 생성 성공")
    void createLabel_success() {
        LabelRequestDto.CreateLabelDto request = new LabelRequestDto.CreateLabelDto("refactor", "222222", "##333");

        labelService.createLabel(request);

        List<Label> labels = new ArrayList<>();

        labelRepository.findAll().forEach(labels::add);
        assertThat(labels).hasSize(3);
        assertThat(labels).extracting("name").contains("refactor");
    }

    @Test
    @DisplayName("레이블 수정 성공")
    void updateLabel_success() {
        LabelRequestDto.CreateLabelDto request = new LabelRequestDto.CreateLabelDto("bug", "bugbug", "#aa0000");

        labelService.updateLabel(1L, request);;

        Label updated = labelRepository.findById(1L).orElseThrow();
        assertThat(updated.getName()).isEqualTo("bug");
        assertThat(updated.getColor()).isEqualTo("#aa0000");
    }


    @Test
    @DisplayName("레이블 수정 실패 - 존재하지 않음")
    void updateLabel_notFound() {
        LabelRequestDto.CreateLabelDto request = new LabelRequestDto.CreateLabelDto("re", "rerere", "#000");

        assertThatThrownBy(() -> labelService.updateLabel(999L, request))
            .isInstanceOf(LabelNotFoundException.class);
    }

    @Test
    @DisplayName("레이블 삭제 성공")
    void deleteLabel_success() {
        labelService.deleteLabel(2L);

        assertThat(labelRepository.existsById(2L)).isFalse();
    }

    @Test
    @DisplayName("존재하지 않는 레이블 삭제 시 예외 발생")
    void deleteLabel_notFound() {
        assertThatThrownBy(() -> labelService.deleteLabel(999L))
            .isInstanceOf(LabelNotFoundException.class);
    }


}
