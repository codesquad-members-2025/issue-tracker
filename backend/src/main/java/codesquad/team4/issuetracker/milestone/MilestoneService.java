package codesquad.team4.issuetracker.milestone;

import codesquad.team4.issuetracker.milestone.dto.MilestoneResponseDto;
import codesquad.team4.issuetracker.milestone.dto.MilestoneResponseDto.MilestoneInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MilestoneService {
    private final MilestoneDao milestoneDao;

    public MilestoneResponseDto.MilestoneFilter getFilterMilestones() {
        List<MilestoneInfo> milestones = milestoneDao.findMilestoneForFiltering();

        return MilestoneResponseDto.MilestoneFilter.builder()
                .milestones(milestones)
                .count(milestones.size())
                .build();
    }

    public MilestoneResponseDto.MilestoneCountDto getMilestoneCount() {
        Integer openCount = milestoneDao.countMilestonesByOpenStatus(true);
        Integer closedCount = milestoneDao.countMilestonesByOpenStatus(false);

        return MilestoneResponseDto.MilestoneCountDto.builder()
            .openCount(openCount != null ? openCount : 0)
            .closedCount(closedCount != null ? closedCount : 0)
            .build();
    }
}
