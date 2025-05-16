package CodeSquad.IssueTracker.milestone;



import CodeSquad.IssueTracker.milestone.dto.MilestoneUpdateDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class MilestoneServiceTest {

    @Autowired
    private JdbcTemplatesMilestoneRepository milestoneRepository;

    @Test
    @DisplayName("마일스톤 저장 후 단건 조회")
    void saveAndFindById() {
        Milestone milestone = new Milestone();
        milestone.setName("v1.0 릴리즈");
        milestone.setDescription("첫 번째 배포 마일스톤");
        milestone.setEndDate(LocalDateTime.of(2025, 6, 1, 0, 0));
        milestone.setIsOpen(true);
        milestone.setCreatedAt(LocalDateTime.now());
        milestone.setUpdatedAt(LocalDateTime.now());

        Milestone saved = milestoneRepository.save(milestone);
        Optional<Milestone> found = milestoneRepository.findById(saved.getMilestoneId());

        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("v1.0 릴리즈");
    }

    @Test
    @DisplayName("마일스톤 전체 목록 조회")
    void findAll() {
        Milestone m1 = new Milestone();
        m1.setName("v1.0");
        m1.setIsOpen(true);
        milestoneRepository.save(m1);

        Milestone m2 = new Milestone();
        m2.setName("v2.0");
        m2.setIsOpen(false);
        milestoneRepository.save(m2);

        List<Milestone> list = milestoneRepository.findAll();
        assertThat(list.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    @DisplayName("마일스톤 업데이트")
    void update() {
        Milestone milestone = new Milestone();
        milestone.setName("초안");
        milestone.setIsOpen(true);
        Milestone saved = milestoneRepository.save(milestone);

        MilestoneUpdateDto updateDto = new MilestoneUpdateDto();
        updateDto.setName("최종안");
        updateDto.setDescription("변경된 설명");
        updateDto.setEndDate(LocalDateTime.of(2025, 7, 1, 0, 0));
        updateDto.setIsOpen(false);

        milestoneRepository.update(saved.getMilestoneId(), updateDto);
        Optional<Milestone> updated = milestoneRepository.findById(saved.getMilestoneId());

        assertThat(updated).isPresent();
        assertThat(updated.get().getName()).isEqualTo("최종안");
        assertThat(updated.get().getIsOpen()).isFalse();
    }

    @Test
    @DisplayName("마일스톤 삭제")
    void deleteById() {
        Milestone milestone = new Milestone();
        milestone.setName("삭제 테스트");
        Milestone saved = milestoneRepository.save(milestone);

        milestoneRepository.deleteById(saved.getMilestoneId());
        List<Milestone> all = milestoneRepository.findAll();

        assertThat(all).isEmpty();
    }
}
