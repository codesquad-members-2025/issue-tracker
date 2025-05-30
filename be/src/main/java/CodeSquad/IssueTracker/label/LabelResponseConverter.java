package CodeSquad.IssueTracker.label;

import CodeSquad.IssueTracker.label.dto.LabelResponse;

public class LabelResponseConverter {

    public static LabelResponse toLabelResponse(Label label) {
        return new LabelResponse(label.getLabelId(), label.getName(), label.getDescription(), label.getColor());
    }
}
