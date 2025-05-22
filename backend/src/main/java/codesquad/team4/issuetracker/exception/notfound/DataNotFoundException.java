package codesquad.team4.issuetracker.exception.notfound;

public abstract class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(String message) {
        super(message);
    }
}
