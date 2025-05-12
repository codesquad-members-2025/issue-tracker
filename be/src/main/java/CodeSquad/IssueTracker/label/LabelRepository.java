package CodeSquad.IssueTracker.label;

import java.util.List;

public interface LabelRepository {

    Label save(Label label);

    List<Label> findAll();

    List<Label> findByIssueId(Long issueId);

    void attachLabelToIssue(Long issueId, Long labelId);

}
