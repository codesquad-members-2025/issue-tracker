package codesquad.team4.issuetracker.label;

import codesquad.team4.issuetracker.issue.dto.IssueResponseDto;
import codesquad.team4.issuetracker.label.dto.LabelDto;
import codesquad.team4.issuetracker.label.dto.LabelDto.LabelInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelService {

    private final LabelDao labelDao;

    public LabelDto.LabelFilter getFilterLabels() {
        List<LabelDto.LabelInfo> labels = labelDao.findLabelForFiltering();

        return LabelDto.LabelFilter.builder()
                .labels(labels)
                .count(labels.size())
                .build();
    }
}
