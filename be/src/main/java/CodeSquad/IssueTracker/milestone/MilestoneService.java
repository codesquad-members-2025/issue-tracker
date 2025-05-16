package CodeSquad.IssueTracker.milestone;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;

    public Milestone save(Milestone milestone) {
        return milestoneRepository.save(milestone);
    }

    public Iterable<Milestone> findAll() {
        return milestoneRepository.findAll();
    }

    public void update(Long id, MilestoneUpdateDto updateDto) {
        milestoneRepository.update(id, updateDto);
    }

    public void deleteById(Long id) {
        milestoneRepository.deleteById(id);
    }
}
