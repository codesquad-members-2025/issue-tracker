package elbin_bank.issue_tracker.comment.exception;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(long id) {
        super("Could not find comment with id " + id);
    }
}
