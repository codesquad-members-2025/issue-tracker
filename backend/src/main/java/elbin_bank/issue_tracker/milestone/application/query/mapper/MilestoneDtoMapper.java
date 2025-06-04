package elbin_bank.issue_tracker.milestone.application.query.mapper;

import elbin_bank.issue_tracker.milestone.application.ProgressRateCalculator;
import elbin_bank.issue_tracker.milestone.application.query.dto.MilestoneDto;
import elbin_bank.issue_tracker.milestone.application.query.dto.MilestonesResponseDto;
import elbin_bank.issue_tracker.milestone.infrastructure.query.projection.MilestoneProjection;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MilestoneDtoMapper {

    public MilestonesResponseDto toMilestonesResponseDto(List<MilestoneProjection> milestones) {
        return new MilestonesResponseDto(
                milestones.stream().map(i -> new MilestoneDto(
                        i.id(),
                        i.title(),
                        ProgressRateCalculator.calculate(i.totalIssueCount(), i.closedIssueCount())
                )).toList()
        );
    }

}
