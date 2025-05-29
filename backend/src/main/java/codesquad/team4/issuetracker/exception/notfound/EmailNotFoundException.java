package codesquad.team4.issuetracker.exception.notfound;

import static codesquad.team4.issuetracker.exception.ExceptionMessage.NOT_FOUND_EMAIL;

public class EmailNotFoundException extends DataNotFoundException{
    public EmailNotFoundException() {super(NOT_FOUND_EMAIL);}
}
