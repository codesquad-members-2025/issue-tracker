package codesquad.team4.issuetracker.exception;

public class ExceptionMessage {
    public static final String NO_ISSUE_IDS = "변경할 이슈 ID가 없습니다.";
    public static final String ISSUE_IDS_NOT_FOUND ="요청한 이슈 ID가 존재하지 않습니다.";
    public static final String FILE_UPLOAD_FAILED = "파일 업로드에 실패했습니다.";
    public static final String UPDATE_ISSUE_FAILED =  "이슈가 존재하지 않습니다";
    public static final String NOT_FOUND_ISSUE = "이슈를 찾을 수 없습니다. issueId = ";
    public static final String NOT_FOUND_MILESTONE = "마일스톤을 찾을 수 없습니다. milestoneId = ";
    public static final String NOT_FOUND_LABEL = "존재하지 않는 label ID: ";
    public static final String NOT_FOUND_ASSIGNEE = "존재하지 않는 assignee ID: ";
    public static final String NOT_FOUND_COMMENT = "댓글을 찾을 수 없습니다. commentId = ";
    public static final String INVALID_COMMENT_ACCESS = "댓글이 이슈에 속하지 않습니다.";
    public static final String INVALID_FILTERING_CONDITION = "authorId, assigneeId, commentAuthorId 중 하나만 사용할 수 있습니다.";
    public static final String EMAIL_ALREADY_EXIST = "이미 존재하는 이메일입니다";
    public static final String NICKNAME_ALREADY_EXIST = "이미 존재하는 닉네임입니다";
    public static final String USER_BY_EMAIL_NOT_EXIST = "이메일이 일치하는 회원이 없습니다";
    public static final String USER_PASSWORD_NOT_EQUAL = "비밀번호가 일치하지 않습니다";
}
