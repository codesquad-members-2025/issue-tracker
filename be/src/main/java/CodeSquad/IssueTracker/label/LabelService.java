package CodeSquad.IssueTracker.label;

import CodeSquad.IssueTracker.label.dto.LabelUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;

    public Label save(Label label) {
        return labelRepository.save(label);
    }

    public Iterable<Label> findAll() {
        return labelRepository.findAll();
    }

    public Iterable<Label> findByIssueId(Long issueId) {
        return labelRepository.findByIssueId(issueId);
    }

    public void update(Long labelId, LabelUpdateDto updateParam) {
        labelRepository.update(labelId, updateParam);
    }

    public void deleteById(Long labelId) {
        labelRepository.deleteById(labelId);
    }


}
