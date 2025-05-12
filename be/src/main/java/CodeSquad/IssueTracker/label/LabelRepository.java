package CodeSquad.IssueTracker.label;

import java.util.List;

public interface LabelRepository {

    Label save(Label label);

    List<Label> findAll();

    List<Label> findByIssueId(Long issueId);

    void update(Long labelId, LabelUpdateDto updateParam);

    void deleteById(Long labelId);

}
