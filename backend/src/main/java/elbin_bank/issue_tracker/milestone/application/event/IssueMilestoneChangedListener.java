package elbin_bank.issue_tracker.milestone.application.event;

import elbin_bank.issue_tracker.issue.application.command.event.IssueMilestoneChangedEvent;
import elbin_bank.issue_tracker.issue.application.command.event.IssueMilestoneCreateEvent;
import elbin_bank.issue_tracker.issue.application.command.event.IssueMilestoneDeleteEvent;
import elbin_bank.issue_tracker.issue.application.command.event.IssueMilestoneStateEvent;
import elbin_bank.issue_tracker.milestone.domain.MilestoneCommandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IssueMilestoneChangedListener {

    private final MilestoneCommandRepository milestoneCommandRepository;

    @EventListener
    public void onIssueChangedMilestoneEvent(IssueMilestoneChangedEvent event) {
        Long oldMs = event.oldMilestoneId();
        Long newMs = event.newMilestoneId();
        boolean isClosed = event.isClosed();

        if (oldMs != null) {
            milestoneCommandRepository.adjustTotalIssues(oldMs, -1);
            if (isClosed) {
                milestoneCommandRepository.adjustClosedIssues(oldMs, -1);
            }
        }
        if (newMs != null) {
            milestoneCommandRepository.adjustTotalIssues(newMs, +1);
            if (isClosed) {
                milestoneCommandRepository.adjustClosedIssues(newMs, +1);
            }
        }
    }

    @EventListener
    public void onIssueMilestoneStateEvent(IssueMilestoneStateEvent event) {
        Long milestoneId = event.milestoneId();
        if (milestoneId != null) {
            if (event.isClosed()) {
                milestoneCommandRepository.adjustClosedIssues(milestoneId, +1);
            } else {
                milestoneCommandRepository.adjustClosedIssues(milestoneId, -1);
            }
        }
    }

    @EventListener
    public void onIssueCreateMilestoneEvent(IssueMilestoneCreateEvent event) {
        Long milestoneId = event.milestoneId();
        if (milestoneId != null) {
            milestoneCommandRepository.adjustTotalIssues(milestoneId, +1);
        }
    }

    @EventListener
    public void onIssueDeleteMilestoneEvent(IssueMilestoneDeleteEvent event) {
        Long milestoneId = event.milestoneId();
        if (milestoneId != null) {
            milestoneCommandRepository.adjustTotalIssues(milestoneId, -1);
            if (event.isClosed()) {
                milestoneCommandRepository.adjustClosedIssues(milestoneId, -1);
            }
        }
    }

}
