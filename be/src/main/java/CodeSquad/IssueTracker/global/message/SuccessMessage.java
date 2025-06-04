package CodeSquad.IssueTracker.global.message;

import lombok.Getter;

@Getter
public enum SuccessMessage {
    GITHUB_LOGIN_SUCCESS("Github 로그인을 성공했습니다"),
    ISSUE_DETAIL_FETCH_SUCCESS("이슈 상세 정보를 성공적으로 조회했습니다."),
    COMMENT_DELETE_SUCCESS("코멘트 삭제가 정상적으로 처리되었습니다."),
    ISSUE_DELETE_SUCCESS("이슈 삭제가 정상적으로 치리되었습니다."),
    LABEL_DELETE_SUCCESS("라벨 삭제가 정상적으로 처리되었습니다."),
    MILESTONE_DELETE_SUCCESS("마일스톤 삭제가 정상적으로 처리되었습니다.");

    private final String message;

    SuccessMessage(String message) {
        this.message = message;
    }
}
