package elbin_bank.issue_tracker.issue.infrastructure.query.strategy;

import elbin_bank.issue_tracker.issue.application.query.dsl.FilterCriteria;

import java.util.Map;

public interface FilterStrategy {

    /**
     * 이 필터가 이번 요청(FilterCriteria)에 적용되어야 하는지 여부
     */
    boolean supports(FilterCriteria criteria);

    /**
     * JOIN 절에 추가할 SQL fragment (ex: " JOIN assignee …")
     */
    void applyJoin(StringBuilder join, FilterCriteria criteria);

    /**
     * WHERE 절에 추가할 SQL fragment, 파라미터 세팅
     */
    void applyWhere(StringBuilder where, Map<String, Object> params, FilterCriteria criteria);

    /**
     * HAVING 절에 추가할 SQL fragment, 파라미터 세팅
     */
    void applyHaving(StringBuilder having, Map<String, Object> params, FilterCriteria criteria);

}
