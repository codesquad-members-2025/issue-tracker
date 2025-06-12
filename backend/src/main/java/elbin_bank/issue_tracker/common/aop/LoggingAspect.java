package elbin_bank.issue_tracker.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    // 1. 서비스 레이어의 모든 public 메서드에 적용
    @Pointcut("execution(public * elbin_bank.issue_tracker..application..*(..))")
    public void serviceMethods() {
    }

    //    정상 종료 시점에 인자·반환값·실행 시간까지 한 번에 로깅
    @Around("serviceMethods()")
    public Object logWithTiming(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();

        // 메서드 이름, 클래스 이름
        String targetClass = pjp.getSignature().getDeclaringTypeName();
        String methodName = pjp.getSignature().getName();

        // 1) 인자 정보
        Object[] args = pjp.getArgs();
        String argsString = argsToString(args);

        try {
            // 원본 메서드 실행
            Object result = pjp.proceed();

            long duration = System.currentTimeMillis() - start;

            // 2) 정상 반환된 경우: 인자·반환값·실행시간을 한 번에 로깅
            log.info("✔ [Executed] {}.{}({}) => {} ({}ms)",
                    targetClass,
                    methodName,
                    argsString,
                    result,
                    duration);

            return result;
        } catch (Throwable ex) {
            long duration = System.currentTimeMillis() - start;

            // 3) 예외 발생 시점에도 인자·예외 메시지·실행시간을 한 번에 로깅
            log.error("✘ [Error] {}.{}({}) threw {} ({}ms)",
                    targetClass,
                    methodName,
                    argsString,
                    ex.getClass().getSimpleName() + ": " + ex.getMessage(),
                    duration);

            throw ex;
        }
    }

    private String argsToString(Object[] args) {
        if (args == null || args.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            sb.append(arg).append(", ");
        }
        // 맨 끝의 “, ” 제거
        return sb.substring(0, sb.length() - 2);
    }

}
