package elbin_bank.issue_tracker.milestone.exception;

public class MilestoneNotFoundException extends RuntimeException {
    public MilestoneNotFoundException(Long id) {
        super("Could not find milestone with id " + id);
    }
}
