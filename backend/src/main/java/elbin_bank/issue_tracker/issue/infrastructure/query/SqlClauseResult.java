package elbin_bank.issue_tracker.issue.infrastructure.query;

import java.util.Map;


public record SqlClauseResult(
        String joinClause,
        String whereClause,
        String havingClause,
        Map<String, Object> params
) {
    /**
     * build 시점에 AND 접두어 제거 + HAVING 키워드 붙이기
     */
    public static SqlClauseResult of(
            String joinPart,
            StringBuilder wherePart,
            StringBuilder havingPart,
            Map<String, Object> params
    ) {
        String whereSql = wherePart.toString(); // ex. " WHERE 1=1 AND ... AND ..."
        String havingSql = "";
        if (!havingPart.isEmpty()) {
            // havingPart 예: " AND cond1 AND cond2" 가장 앞의 AND 제거 > HAVING절은 AND로 이어붙여야하는데 첫 AND는 생략되어야하기 때문
            String withoutAnd = havingPart.substring(" AND ".length());
            havingSql = " HAVING " + withoutAnd;
        }
        return new SqlClauseResult(joinPart, whereSql, havingSql, params);
    }

}
