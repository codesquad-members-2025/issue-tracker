package codesquad.team4.issuetracker.count;

import codesquad.team4.issuetracker.count.dto.IssueCountDto;
import codesquad.team4.issuetracker.count.dto.MilestoneCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountService {
    private final CountDao countDao;

    public IssueCountDto getIssueCounts() {
        return countDao.getIssueCounts();
    }

    public MilestoneCountDto getMilestoneCounts() {
        return countDao.getMilestoneCount();
    }
}
