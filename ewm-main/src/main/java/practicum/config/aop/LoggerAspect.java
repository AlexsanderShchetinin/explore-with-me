package practicum.config.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggerAspect {

    @Pointcut("execution(* *(..)) && @within(LoggingAndExecutingTime)")
    public void logExecutingTimePointcut() {
    }

    @Around("logExecutingTimePointcut()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature method = joinPoint.getSignature();
        log.info("Начало выполнения метода: {} со значением параметров: {}", method.toLongString(), joinPoint.getArgs());
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;
        log.info("Метод {} выполнен за {} мс", method.toShortString(), executionTime);
        return proceed;
    }

    @AfterReturning(pointcut = "logExecutingTimePointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("Метод {} вернул: {}", methodName, result);
    }


}
