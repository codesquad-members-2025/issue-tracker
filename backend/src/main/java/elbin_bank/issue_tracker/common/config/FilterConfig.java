package elbin_bank.issue_tracker.common.config;

import elbin_bank.issue_tracker.issue.infrastructure.query.SqlBuilder;
import elbin_bank.issue_tracker.issue.infrastructure.query.strategy.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class FilterConfig {

    @Bean
    public List<FilterStrategy> filterStrategies() {
        return List.of(
                new StateFilterStrategy(),
                new LabelFilterStrategy(),
                new MilestoneFilterStrategy(),
                new AssigneeFilterStrategy()
        );
    }

    @Bean
    public SqlBuilder filterStrategyContext(List<FilterStrategy> strategies) {
        return new SqlBuilder(strategies);
    }

}
