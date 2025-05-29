package elbin_bank.issue_tracker.issue.application.query.dsl;

import java.util.ArrayList;
import java.util.List;

public class DslParser {

    private DslParser() {
    }

    public static FilterCriteria parse(String dsl) {
        if (isEmpty(dsl)) {
            return new FilterCriteria(false, List.of(), List.of(), null, null, false);
        }

        ParsedValues parsed = parseTokens(dsl);

        ensureDefaultState(parsed.states);

        boolean unsatisfiable = isUnsatisfiable(parsed);

        return toCriteria(parsed, unsatisfiable);
    }

    private static boolean isEmpty(String dsl) {
        return dsl == null || dsl.isBlank();
    }

    private static ParsedValues parseTokens(String dsl) {
        List<Boolean> states = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        List<String> assignees = new ArrayList<>();
        List<String> authors = new ArrayList<>();
        List<String> milestones = new ArrayList<>();

        int i = 0;
        while (i < dsl.length()) {
            int colonIdx = dsl.indexOf(':', i);
            if (colonIdx == -1) break;

            String key = dsl.substring(i, colonIdx).trim();
            i = colonIdx + 1;

            ExtractResult result = extractValue(dsl, i);
            i = result.nextIndex();
            String value = result.value();

            switch (key) {
                case "state" -> states.add(parseState(value));
                case "label" -> labels.add(value);
                case "assignee" -> assignees.add(value);
                case "author" -> authors.add(value);
                case "milestone" -> milestones.add(value);
            }

            while (i < dsl.length() && Character.isWhitespace(dsl.charAt(i))) i++;
        }

        return new ParsedValues(states, labels, assignees, authors, milestones);
    }

    private static boolean parseState(String value) {
        return switch (value) {
            case "open" -> false;
            case "closed" -> true;
            default -> throw new IllegalArgumentException("Invalid state: " + value);
        };
    }

    private static ExtractResult extractValue(String dsl, int start) {
        StringBuilder buf = new StringBuilder();
        int i = start;

        if (i < dsl.length() && dsl.charAt(i) == '"') {
            i++;
            while (i < dsl.length() && dsl.charAt(i) != '"') {
                buf.append(dsl.charAt(i++));
            }
            i++;
        } else {
            while (i < dsl.length() && !Character.isWhitespace(dsl.charAt(i))) {
                buf.append(dsl.charAt(i++));
            }
        }

        return new ExtractResult(buf.toString(), i);
    }

    private static void ensureDefaultState(List<Boolean> states) {
        if (states.isEmpty()) {
            states.add(false); // 기본: open
        }
    }

    private static boolean isUnsatisfiable(ParsedValues parsed) {
        return parsed.states.size() > 1
                || parsed.authors.size() > 1
                || parsed.milestones.size() > 1;
    }

    private static FilterCriteria toCriteria(ParsedValues parsed, boolean unsatisfiable) {
        boolean isClosed = parsed.states.getFirst();
        String author = parsed.authors.size() == 1 ? parsed.authors.getFirst() : null;
        String milestone = parsed.milestones.size() == 1 ? parsed.milestones.getFirst() : null;

        return new FilterCriteria(
                isClosed,
                List.copyOf(parsed.labels),
                List.copyOf(parsed.assignees),
                author,
                milestone,
                unsatisfiable
        );
    }

    private record ExtractResult(String value, int nextIndex) {
    }

    private record ParsedValues(
            List<Boolean> states,
            List<String> labels,
            List<String> assignees,
            List<String> authors,
            List<String> milestones
    ) {
    }

}
