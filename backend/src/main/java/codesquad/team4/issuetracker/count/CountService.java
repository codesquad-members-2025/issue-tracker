package codesquad.team4.issuetracker.count;

import codesquad.team4.issuetracker.count.dto.IssueCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountService {
    private final IssueCountDao issueCountDao;

    public IssueCountDto getIssueCounts() {
        return issueCountDao.getIssueCounts();
    }
}
