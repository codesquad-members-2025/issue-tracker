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
    private final CountDao issueCountDao;

    /** 생성 → open +1 */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreated(IssueEvent.Created e) {
        issueCountDao.incrementIssueOpen();
    }

    /** 상태 변경 → open/closed 증감 */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onStatusChanged(IssueEvent.StatusChanged e) {
        if (e.isOldIsOpen() && !e.isNewIsOpen()) {
            issueCountDao.decrementIssueOpen();
            issueCountDao.incrementIssueClosed();
        } else if (!e.isOldIsOpen() && e.isNewIsOpen()) {
            issueCountDao.incrementIssueOpen();
            issueCountDao.decrementIssueClosed();
        }
    }

    /** 삭제 → wasOpen 여부에 따라 –1 */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onDeleted(IssueEvent.Deleted e) {
        if (e.isWasOpen()) {
            issueCountDao.decrementIssueOpen();
        } else {
            issueCountDao.decrementIssueClosed();
        }
    }

}
