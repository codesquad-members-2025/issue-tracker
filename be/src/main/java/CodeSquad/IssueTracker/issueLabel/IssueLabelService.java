package CodeSquad.IssueTracker.issueLabel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueLabelService {

    private final IssueLabelRepository issueLabelRepository;

    @Transactional
    public void assignLabels(Long issueId, List<Long> labelIds) {

        issueLabelRepository.deleteByIssueId(issueId);

        for (Long labelId : labelIds) {
            IssueLabel entity = new IssueLabel();
            entity.setIssueId(issueId);
            entity.setLabelId(labelId);
            issueLabelRepository.save(entity);
        }
    }
}
