package elbin_bank.issue_tracker.issue.infrastructure.readmodel;

import elbin_bank.issue_tracker.issue.application.event.IssueClosedEvent;
import elbin_bank.issue_tracker.issue.application.event.IssueCreatedEvent;
import elbin_bank.issue_tracker.issue.application.event.IssueReopenedEvent;
import elbin_bank.issue_tracker.issue.domain.IssueCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IssueStatusCountUpdater {

    private static final String OPEN_STATUS = "OPEN";
    private static final String CLOSE_STATUS = "CLOSED";

    private final IssueCommandRepository issueCommandRepository;

    @EventListener
    public void onCreated(IssueCreatedEvent e) {
        issueCommandRepository.adjustCount("OPEN", 1);
    }

    @EventListener
    public void onClosed(IssueClosedEvent e) {
        issueCommandRepository.adjustCount(OPEN_STATUS, -1);
        issueCommandRepository.adjustCount(CLOSE_STATUS, 1);
    }

    @EventListener
    public void onReopened(IssueReopenedEvent e) {
        issueCommandRepository.adjustCount(CLOSE_STATUS, -1);
        issueCommandRepository.adjustCount(OPEN_STATUS, 1);
    }

}
