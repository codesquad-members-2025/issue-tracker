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
        int open = countDao.getCount(CountDao.ISSUE_OPEN);
        int closed = countDao.getCount(CountDao.ISSUE_CLOSED);
        return new IssueCountDto(open, closed);
    }

    public MilestoneCountDto getMilestoneCounts() {
        int open = countDao.getCount(CountDao.MILESTONE_OPEN);
        int closed = countDao.getCount(CountDao.MILESTONE_CLOSED);
        return new MilestoneCountDto(open, closed);
    }
}
