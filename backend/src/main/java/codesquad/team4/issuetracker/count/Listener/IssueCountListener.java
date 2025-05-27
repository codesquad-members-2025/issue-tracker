package codesquad.team4.issuetracker.count.Listener;

import codesquad.team4.issuetracker.count.IssueCountDao;
import codesquad.team4.issuetracker.issue.IssueEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class IssueCountListener {
    private final IssueCountDao issueCountDao;

    /** 생성 → open +1 */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreated(IssueEvent.Created e) {
        issueCountDao.incrementOpenCount();
    }

    /** 상태 변경 → open/closed 증감 */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onStatusChanged(IssueEvent.StatusChanged e) {
        if (e.isOldIsOpen() && !e.isNewIsOpen()) {
            issueCountDao.decrementOpenCount();
            issueCountDao.incrementClosedCount();
        } else if (!e.isOldIsOpen() && e.isNewIsOpen()) {
            issueCountDao.incrementOpenCount();
            issueCountDao.decrementClosedCount();
        }
    }

    /** 삭제 → wasOpen 여부에 따라 –1 */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onDeleted(IssueEvent.Deleted e) {
        if (e.isWasOpen()) {
            issueCountDao.decrementOpenCount();
        } else {
            issueCountDao.decrementClosedCount();
        }
    }

}
