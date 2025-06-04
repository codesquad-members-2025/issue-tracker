package elbin_bank.issue_tracker.label.exception;

public class LabelNameDuplicateException extends RuntimeException {
    public LabelNameDuplicateException(String message) {
        super(message);
    }
}
