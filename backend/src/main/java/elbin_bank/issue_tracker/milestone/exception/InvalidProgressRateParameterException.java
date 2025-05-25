package elbin_bank.issue_tracker.milestone.exception;

public class InvalidProgressRateParameterException extends RuntimeException {
    public InvalidProgressRateParameterException(long totalIssues, long closedIssues) {
        super("진행률 계산용 파라미터가 유효하지 않습니다: totalIssues="
                + totalIssues + ", closedIssues=" + closedIssues);
    }
}
