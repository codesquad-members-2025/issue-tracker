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
        countDao.incrementMilestoneOpen();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onStatusChanged(MilestoneEvent.StatusChanged e) {
        if (e.isOldIsOpen() && !e.isNewIsOpen()) {
            countDao.decrementMilestoneOpen();
            countDao.incrementMilestoneClosed();
        } else if (!e.isOldIsOpen() && e.isNewIsOpen()) {
            countDao.incrementMilestoneOpen();
            countDao.decrementMilestoneClosed();
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onDeleted(MilestoneEvent.Deleted e) {
        if (e.isWasOpen()) {
            countDao.decrementMilestoneOpen();
        } else {
            countDao.decrementMilestoneClosed();
        }
    }

}
