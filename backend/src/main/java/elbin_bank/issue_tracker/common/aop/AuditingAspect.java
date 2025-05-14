package elbin_bank.issue_tracker.common.aop;

import elbin_bank.issue_tracker.common.domain.BaseEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class AuditingAspect {

    @Before("execution(* org.springframework.data.repository.CrudRepository+.save(..))")
    public void touchDates(JoinPoint jp) {
        Object arg = jp.getArgs()[0];
        if (arg instanceof BaseEntity entity) {
            LocalDateTime now = LocalDateTime.now();
            if (entity.getCreatedAt() == null) {
                entity.setCreatedAt(now);
            }
            entity.setUpdatedAt(now);
        }
    }

}
