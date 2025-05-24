package elbin_bank.issue_tracker.common.config;

import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.data.relational.core.mapping.RelationalPersistentProperty;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SnakeCaseNamingStrategy implements NamingStrategy {

    @Override
    public @NonNull String getColumnName(RelationalPersistentProperty property) {
        return toSnakeCase(property.getName());
    }

    @Override
    public @NonNull String getTableName(Class<?> type) {
        return toSnakeCase(type.getSimpleName());
    }

    private String toSnakeCase(String input) {
        return input.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

}
