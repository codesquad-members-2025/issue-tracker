package elbin_bank.issue_tracker.milestone.application.command;

import elbin_bank.issue_tracker.milestone.application.query.repository.MilestoneQueryRepository;
import elbin_bank.issue_tracker.milestone.domain.Milestone;
import elbin_bank.issue_tracker.milestone.domain.MilestoneCommandRepository;
import elbin_bank.issue_tracker.milestone.exception.MilestoneNotFoundException;
import elbin_bank.issue_tracker.milestone.infrastructure.query.projection.MilestoneUpdateProjection;
import elbin_bank.issue_tracker.milestone.presentation.command.dto.request.MilestoneCreateRequestDto;
import elbin_bank.issue_tracker.milestone.presentation.command.dto.request.MilestoneUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MilestoneCommandService {

    private final MilestoneCommandRepository milestoneCommandRepository;
    private final MilestoneQueryRepository milestoneQueryRepository;

    @Transactional
    public void createMilestone(MilestoneCreateRequestDto milestoneCreateRequestDto) {
        Milestone milestone = Milestone.of(milestoneCreateRequestDto.title(), milestoneCreateRequestDto.expiredAt(), milestoneCreateRequestDto.description());

        milestoneCommandRepository.save(milestone);
    }

    @Transactional
    public void updateMilestone(MilestoneUpdateRequestDto milestoneUpdateRequestDto, Long id) {
        MilestoneUpdateProjection milestone = milestoneQueryRepository.findById(id)
                .orElseThrow(() -> new MilestoneNotFoundException(id));

        LocalDate expiredAt = milestoneUpdateRequestDto.expiredAt() != null ? LocalDate.parse(milestoneUpdateRequestDto.expiredAt()) : null;

        milestoneCommandRepository.update(milestone, milestoneUpdateRequestDto.title(), milestoneUpdateRequestDto.description(), expiredAt);
    }

    @Transactional
    public void deleteMilestone(Long id) {
        milestoneCommandRepository.deleteById(id);
    }
}
