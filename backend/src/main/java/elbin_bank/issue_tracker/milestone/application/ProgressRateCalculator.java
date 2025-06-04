package elbin_bank.issue_tracker.milestone.application;

import elbin_bank.issue_tracker.milestone.exception.InvalidProgressRateParameterException;

public class ProgressRateCalculator {

    private ProgressRateCalculator() {
    }

    /**
     * 총 이슈 수 대비 닫힌 이슈의 비율을 0~100 사이의 정수(내림)로 계산합니다.
     *
     * @param totalIssues  총 이슈 수 (>= 0)
     * @param closedIssues 닫힌 이슈 수 (>= 0)
     * @return 닫힌 비율(%) - totalIssues가 0이면 0, closedIssues > totalIssues면 100을 초과하지 않음
     * @throws InvalidProgressRateParameterException closedIssues < 0 이거나 totalIssues < 0 일 때 || totalIssues < closedIssues 일 때
     */
    public static int calculate(long totalIssues, long closedIssues) {
        if (totalIssues < 0 || closedIssues < 0) {
            throw new InvalidProgressRateParameterException(totalIssues, closedIssues);
        }
        if (totalIssues < closedIssues) {
            throw new InvalidProgressRateParameterException(closedIssues, totalIssues);
        }
        if (totalIssues == 0) {
            return 0;
        }
        double raw = (double) closedIssues * 100 / totalIssues;

        return (int) raw;
    }

}
