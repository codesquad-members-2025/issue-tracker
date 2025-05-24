package elbin_bank.issue_tracker.issue.infrastructure.query.strategy;

import java.util.Map;

public interface FilterStrategy {

    /**
     * 키워드에 해당하면 true,
     * ex p.startsWith("state:")
     */
    boolean supports(String part);

    /**
     * SQL WHERE 절 조각 (AND 생략)
     * ex "i.is_closed = :closed"
     */
    String getSqlPart();

    /**
     * 파라미터 매핑
     * ex Map.of("closed", true)
     */
    Map<String, Object> getParameters(String part);

}
