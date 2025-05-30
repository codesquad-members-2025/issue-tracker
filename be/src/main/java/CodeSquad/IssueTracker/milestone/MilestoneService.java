package CodeSquad.IssueTracker.milestone;

import CodeSquad.IssueTracker.milestone.dto.MilestoneListResponse;
import CodeSquad.IssueTracker.milestone.dto.MilestoneResponse;
import CodeSquad.IssueTracker.milestone.dto.MilestoneUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    public MilestoneResponse findMilestoneResponsesByIssueId(Long issueId) {
        return milestoneRepository.findMilestoneResponsesByIssueId(issueId);
    }

    public MilestoneListResponse getMilestonesByStatus(boolean isOpen) {
        List<Milestone> milestones = milestoneRepository.findByStatus(isOpen);
        milestones.forEach(this::setCalculateProcessingRate);
        List<MilestoneResponse> milestoneResponses = milestones.stream()
                .map(MilestoneResponse::convertMilestoneResponse)
                .toList();
        return new MilestoneListResponse(
                milestoneResponses
                ,countOpenAndClose(true)
                ,countOpenAndClose(false)
        );
    }

    private Integer countOpenAndClose(boolean isOpen){
        return milestoneRepository.countByStatus(isOpen);
    }

    private void setCalculateProcessingRate(Milestone milestone){
        milestone.setProcessingRate(milestoneRepository.calculateProcessingRate(milestone));
    }
}
