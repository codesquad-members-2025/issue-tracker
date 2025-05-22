package codesquad.team4.issuetracker.exception.badrequest;

public abstract class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }
}
