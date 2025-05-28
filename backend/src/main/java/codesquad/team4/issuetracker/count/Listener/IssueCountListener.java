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
        countDao.increment(CountDao.ISSUE_OPEN);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onStatusChanged(IssueEvent.StatusChanged e) {
        if (e.isClosing()) {
            countDao.decrement(CountDao.ISSUE_OPEN);
            countDao.increment(CountDao.ISSUE_CLOSED);
        } else if (e.isOpening()) {
            countDao.increment(CountDao.ISSUE_OPEN);
            countDao.decrement(CountDao.ISSUE_CLOSED);
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onDeleted(IssueEvent.Deleted e) {
        if (e.isWasOpen()) {
            countDao.decrement(CountDao.ISSUE_OPEN);
        } else {
            countDao.decrement(CountDao.ISSUE_CLOSED);
        }
    }

}
