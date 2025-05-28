package codesquad.team4.issuetracker.exception.conflict;

public class EmailAlreadyExistException extends ConflictException{
    public EmailAlreadyExistException(String message) {
        super (message);
    }
}
