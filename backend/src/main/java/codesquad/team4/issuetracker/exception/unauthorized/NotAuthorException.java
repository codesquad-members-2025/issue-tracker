package codesquad.team4.issuetracker.exception.unauthorized;

import static codesquad.team4.issuetracker.exception.ExceptionMessage.NOT_AUTHOR;

public class NotAuthorException extends UnauthorizedException{
    public NotAuthorException() {super(NOT_AUTHOR);}
}
