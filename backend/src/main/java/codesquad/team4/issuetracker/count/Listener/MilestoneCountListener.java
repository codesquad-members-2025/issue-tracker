package codesquad.team4.issuetracker.count.Listener;

import codesquad.team4.issuetracker.count.CountDao;
import codesquad.team4.issuetracker.milestone.MilestoneEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class MilestoneCountListener {
    private final CountDao countDao;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreated(MilestoneEvent.Created e) {
        countDao.increment(CountDao.MILESTONE_OPEN);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onStatusChanged(MilestoneEvent.StatusChanged e) {
        if (e.isClosing()) {
            countDao.decrement(CountDao.MILESTONE_OPEN);
            countDao.increment(CountDao.MILESTONE_CLOSED);
        } else if (e.isOpening()) {
            countDao.increment(CountDao.MILESTONE_OPEN);
            countDao.decrement(CountDao.MILESTONE_CLOSED);
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onDeleted(MilestoneEvent.Deleted e) {
        if (e.isWasOpen()) {
            countDao.decrement(CountDao.MILESTONE_OPEN);
        } else {
            countDao.decrement(CountDao.MILESTONE_CLOSED);
        }
    }

}
