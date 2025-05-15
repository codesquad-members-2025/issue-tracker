package CodeSquad.IssueTracker.label;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class LabelServiceTest {

    @Autowired
    private LabelService labelService;

    @Test
    void save() {
        // given
        Label label = new Label();
        label.setName("bug");
        label.setColor("red");
        label.setDescription("버그 관련 라벨");
        label.setCreatedAt(LocalDateTime.now());

        // when
        Label saved = labelService.save(label);

        // then
        assertThat(saved.getLabelId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("bug");
        assertThat(saved.getDescription()).isEqualTo("버그 관련 라벨");
    }

    @Test
    void findAll() {
        // given
        Label label1 = new Label();
        label1.setName("feature");
        label1.setColor("blue");
        label1.setDescription("기능 라벨");
        label1.setCreatedAt(LocalDateTime.now());
        labelService.save(label1);

        Label label2 = new Label();
        label2.setName("urgent");
        label2.setColor("yellow");
        label2.setDescription("긴급 라벨");
        label2.setCreatedAt(LocalDateTime.now());
        labelService.save(label2);

        // when
        Iterable<Label> all = labelService.findAll();

        // then
        assertThat(all).anyMatch(label -> label.getName().equals("feature"));
        assertThat(all).anyMatch(label -> label.getName().equals("urgent"));
    }

    @Test
    void update() {
        // given
        Label label = new Label();
        label.setName("old");
        label.setColor("gray");
        label.setDescription("구버전");
        label.setCreatedAt(LocalDateTime.now());
        Label saved = labelService.save(label);

        LabelUpdateDto updateDto = new LabelUpdateDto("new", "신버전", "black",LocalDateTime.now());

        // when
        labelService.update(saved.getLabelId(), updateDto);

        // then
        Label updated = labelService.findAll()
                .iterator()
                .next(); // 간단히 첫 번째 요소로 확인 (실제로는 ID 기반 조회가 이상적)

        assertThat(updated.getName()).isEqualTo("new");
        assertThat(updated.getColor()).isEqualTo("black");
        assertThat(updated.getDescription()).isEqualTo("신버전");
    }

    @Test
    void deleteById() {
        // given
        Label label = new Label();
        label.setName("toDelete");
        label.setColor("white");
        label.setDescription("삭제 대상");
        label.setCreatedAt(LocalDateTime.now());
        Label saved = labelService.save(label);

        // when
        labelService.deleteById(saved.getLabelId());

        // then
        boolean exists = false;
        for (Label l : labelService.findAll()) {
            if (l.getLabelId().equals(saved.getLabelId())) {
                exists = true;
                break;
            }
        }

        assertThat(exists).isFalse();
    }

}
