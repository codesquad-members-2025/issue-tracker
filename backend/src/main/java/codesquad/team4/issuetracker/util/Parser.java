package codesquad.team4.issuetracker.util;

import codesquad.team4.issuetracker.issue.dto.IssueRequestDto;
import codesquad.team4.issuetracker.issue.dto.IssueRequestDto.IssueFilterParamDto;
import codesquad.team4.issuetracker.issue.dto.IssueRequestDto.IssueFilterParamDto.OpenStatus;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static IssueRequestDto.IssueFilterParamDto parseFilterCondition(String query) {
        String[] conditions = query.split(" ");

        String state = "open";
        Long authorId = null;
        Long assigneeId = null;
        Long milestoneId = null;
        Long commentAuthorId = null;
        List<Long> labelIds = new ArrayList<>();

        for (String condition : conditions) {
            if (!condition.contains(":")) continue;

            String[] parts = condition.split(":", 2);
            String key = parts[0];
            String value = parts[1];

            switch (key) {
                case "state":
                    state = value;
                    break;
                case "authorId":
                    authorId = parseLong(value);
                    break;
                case "assigneeId":
                    assigneeId = parseLong(value);
                    break;
                case "milestoneId":
                    milestoneId = parseLong(value);
                    break;
                case "commentAuthorId":
                    commentAuthorId = parseLong(value);
                    break;
                case "labelId":
                    Long labelId = parseLong(value);
                    if (labelId != null) {
                        labelIds.add(labelId);
                    }
                    break;
            }
        }

        return IssueFilterParamDto.builder()
            .authorId(authorId)
            .assigneeId(assigneeId)
            .milestoneId(milestoneId)
            .commentAuthorId(commentAuthorId)
            .status(OpenStatus.fromValue(state))
            .labelIds(labelIds)
            .build();
    }

    private static Long parseLong(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
