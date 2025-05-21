package t1.school.task_logstarter.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Aspect
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private final String level;

    public LoggingAspect(String level) {
        this.level = level;
    }

    @Before("@annotation(t1.school.task_logstarter.aspects.annotations.LogStart)")
    public void logStart(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        logByLevel("Метод " + methodName + " начал выполняться");
    }

    @Around("@annotation(t1.school.task_logstarter.aspects.annotations.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        long startTime = System.currentTimeMillis();
        boolean isResultSucceed = false;
        try {
            Object result = joinPoint.proceed();
            isResultSucceed = true;
            return result;
        } finally {
            long endTime = System.currentTimeMillis();
            long execTime = endTime - startTime;
            if (isResultSucceed) {
                logByLevel("Метод " + methodName + " выполнился успешно после " + execTime + " мс");
            } else {
                logByLevel("Метод " + methodName + " завершился с ошибкой после " + execTime + " мс");
            }
        }
    }

    @After("@annotation(t1.school.task_logstarter.aspects.annotations.LogEnd)")
    public void logEnd(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        logByLevel("Метод " + methodName + " закончил свое выполнение");
    }

    @AfterReturning(
            pointcut = "@annotation(t1.school.task_logstarter.aspects.annotations.LogResult)",
            returning = "result"
    )
    public void logResult(JoinPoint joinPoint, Object result) {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        logByLevel("Метод " + methodName + " вернул в качестве результата " + result);
    }

    @AfterThrowing(
            pointcut = "@annotation(t1.school.task_logstarter.aspects.annotations.LogException)",
            throwing = "exception"
    )
    public void logException(JoinPoint joinPoint, Exception exception) {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        Object[] args = joinPoint.getArgs();
        logByLevel(
                "Метод " + methodName + " после запуска с аргументами " + Arrays.toString(args) +
                        " завершился с ошибкой " + exception.getClass().getName() +
                        " и сообщением: " + exception.getMessage()
        );
    }

    private void logByLevel(String message) {
        switch (level) {
            case "DEBUG" -> logger.debug(message);
            case "WARN" -> logger.warn(message);
            case "ERROR" -> logger.error(message);
            default -> logger.info(message);
        }
    }
}
