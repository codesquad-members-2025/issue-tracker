package elbin_bank.issue_tracker.common.config;

import elbin_bank.issue_tracker.issue.infrastructure.query.strategy.FilterStrategy;
import elbin_bank.issue_tracker.issue.infrastructure.query.strategy.FilterStrategyContext;
import elbin_bank.issue_tracker.issue.infrastructure.query.strategy.StateFilterStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FilterConfig {

    @Bean
    public List<FilterStrategy> filterStrategies() {
        return List.of(
                new StateFilterStrategy()
        );
    }

    @Bean
    public FilterStrategyContext filterStrategyContext(List<FilterStrategy> strategies) {
        return new FilterStrategyContext(strategies);
    }

}
