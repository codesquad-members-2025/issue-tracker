package CodeSquad.IssueTracker.issue.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class IssueStatusUpdateRequest {
    @JsonProperty("id")
    private List<Long> issueIds;

    @JsonProperty("isOpen")
    private boolean isOpen;
}
