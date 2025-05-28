package elbin_bank.issue_tracker.milestone.application.query;

import elbin_bank.issue_tracker.milestone.application.query.dto.MilestoneShortsResponseDto;
import elbin_bank.issue_tracker.milestone.application.query.dto.MilestonesResponseDto;
import elbin_bank.issue_tracker.milestone.application.query.repository.MilestoneQueryRepository;
import elbin_bank.issue_tracker.milestone.infrastructure.query.projection.MilestoneProjection;
import elbin_bank.issue_tracker.milestone.infrastructure.query.projection.MilestoneShortProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MilestoneQueryService {

    private final MilestoneQueryRepository milestoneQueryRepository;

    public MilestonesResponseDto getMilestones() {
        List<MilestoneProjection> detailed = milestoneQueryRepository.findAllDetailed();

        return new MilestonesResponseDto(detailed);
    }

    public MilestoneShortsResponseDto getMilestonesForSelectBox() {
        List<MilestoneShortProjection> allForSelectBox = milestoneQueryRepository.findAllForSelectBox();

        return new MilestoneShortsResponseDto(allForSelectBox);
    }

}
