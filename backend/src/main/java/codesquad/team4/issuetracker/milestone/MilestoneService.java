package codesquad.team4.issuetracker.milestone;

import codesquad.team4.issuetracker.milestone.dto.MilestoneDto;
import codesquad.team4.issuetracker.milestone.dto.MilestoneDto.MilestoneInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MilestoneService {
    private final MilestoneDao milestoneDao;

    public MilestoneDto.MilestoneFilter getFilterMilestones() {
        List<MilestoneInfo> milestones = milestoneDao.findMilestoneForFiltering();

        return MilestoneDto.MilestoneFilter.builder()
                .milestones(milestones)
                .count(milestones.size())
                .build();
    }
}
