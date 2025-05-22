package codesquad.team4.issuetracker.exception.notfound;

import static codesquad.team4.issuetracker.exception.ExceptionMessage.NOT_FOUND_COMMENT;

public class CommentNotFoundException extends DataNotFoundException {
    public CommentNotFoundException(Long commentId) {
        super(NOT_FOUND_COMMENT + commentId);
    }
}
