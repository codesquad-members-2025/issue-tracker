package codesquad.team4.issuetracker.exception.conflict;

public class NicknameAlreadyExistException extends ConflictException{
    public NicknameAlreadyExistException(String message) {
        super (message);
    }
}
