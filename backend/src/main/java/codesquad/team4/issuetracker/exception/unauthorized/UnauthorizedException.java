package codesquad.team4.issuetracker.exception.unauthorized;

abstract public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message) {
        super(message);
    }
}
