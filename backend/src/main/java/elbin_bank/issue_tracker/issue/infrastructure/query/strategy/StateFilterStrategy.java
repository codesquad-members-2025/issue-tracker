package elbin_bank.issue_tracker.issue.infrastructure.query.strategy;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StateFilterStrategy implements FilterStrategy {

    @Override
    public boolean supports(String part) {
        return part.startsWith("state:");
    }

    @Override
    public String getSqlPart() {
        return "i.is_closed = :closed";
    }

    @Override
    public Map<String, Object> getParameters(String part) {
        boolean closed = part.equals("state:closed");

        return Map.of("closed", closed);
    }

}
