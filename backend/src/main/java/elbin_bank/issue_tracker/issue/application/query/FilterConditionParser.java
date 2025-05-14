package elbin_bank.issue_tracker.issue.application.query;

public class FilterConditionParser {

    public static FilterCriteria parse(String q) {
        Boolean isClosed = null;

        if (q != null && !q.isBlank()) {
            String[] parts = q.split(" ");

            for (String p : parts) {
                if (p.startsWith("state:")) {
                    boolean open = p.equals("state:open");
                    boolean close = p.equals("state:closed");
                    if (open && close) {
                        throw new IllegalStateException("Filter conditions cannot be open or open closed");
                    }
                    isClosed = close;
                }
            }
        }
        return new FilterCriteria(isClosed);
    }

}
