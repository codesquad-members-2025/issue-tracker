package codesquad.team4.issuetracker.count.Listener;

import codesquad.team4.issuetracker.count.CountDao;
import codesquad.team4.issuetracker.issue.IssueEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class IssueCountListener {
    private final CountDao countDao;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreated(IssueEvent.Created e) {
        countDao.incrementIssueOpen();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onStatusChanged(IssueEvent.StatusChanged e) {
        if (e.isOldIsOpen() && !e.isNewIsOpen()) {
            countDao.decrementIssueOpen();
            countDao.incrementIssueClosed();
        } else if (!e.isOldIsOpen() && e.isNewIsOpen()) {
            countDao.incrementIssueOpen();
            countDao.decrementIssueClosed();
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onDeleted(IssueEvent.Deleted e) {
        if (e.isWasOpen()) {
            countDao.decrementIssueOpen();
        } else {
            countDao.decrementIssueClosed();
        }
    }

}
