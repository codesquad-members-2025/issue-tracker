package codesquad.team4.issuetracker.exception.conflict;

abstract public class ConflictException extends RuntimeException{
    public ConflictException(String message) {
        super(message);
    }
}
