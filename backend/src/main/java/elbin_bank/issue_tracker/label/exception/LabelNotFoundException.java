package elbin_bank.issue_tracker.label.exception;

public class LabelNotFoundException extends RuntimeException {
    public LabelNotFoundException(Long id) {
        super("Could not find label with id " + id);
    }
}
