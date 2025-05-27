package elbin_bank.issue_tracker.issue.application.query.dsl;

import java.util.ArrayList;
import java.util.List;

public class DslParser {

    private DslParser() {
    }

    public static FilterCriteria parse(String dsl) {
        if (dsl == null || dsl.isBlank() || "is:issue".equals(dsl.trim())) {
            return FilterCriteria.empty();
        }

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

            StringBuilder value = new StringBuilder();
            boolean quoted = i < dsl.length() && dsl.charAt(i) == '"';

            if (quoted) {
                i++;
                while (i < dsl.length() && dsl.charAt(i) != '"') {
                    value.append(dsl.charAt(i));
                    i++;
                }
                i++; // skip closing quote
            } else {
                while (i < dsl.length() && !Character.isWhitespace(dsl.charAt(i))) {
                    value.append(dsl.charAt(i));
                    i++;
                }
            }

            while (i < dsl.length() && Character.isWhitespace(dsl.charAt(i))) {
                i++;
            }

            switch (key) {
                case "state" -> {
                    switch (value.toString()) {
                        case "open" -> states.add(false);
                        case "closed" -> states.add(true);
                    }
                }
                case "label" -> labels.add(value.toString());
                case "assignee" -> assignees.add(value.toString());
                case "author" -> authors.add(value.toString());
                case "milestone" -> milestones.add(value.toString());
            }
        }

        if (states.isEmpty()) {
            states.add(false);
        }

        return new FilterCriteria(states, labels, assignees, authors, milestones);
    }

}
