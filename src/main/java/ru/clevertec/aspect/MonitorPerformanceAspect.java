package ru.clevertec.aspect;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.clevertec.config.PerformanceMonitorProperties;

import java.text.MessageFormat;

@Aspect
@Component
@RequiredArgsConstructor
public class MonitorPerformanceAspect {

    private final PerformanceMonitorProperties properties;

    @Pointcut("@annotation(ru.clevertec.annotation.MonitorPerformance)")
    public void isMonitorPerformance() {
    }

    @Around("isMonitorPerformance()")
    public Object addMonitoringPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = 0;

        if (properties.getEnabled()) {
            start = System.currentTimeMillis();
        }

        Object result = joinPoint.proceed();
        long finish = System.currentTimeMillis() - start;

        if (properties.getMinTime() < finish) {
            String methodName = joinPoint.getSignature().getName();
            System.out.println(MessageFormat.format("Method {0} executed in {1}ms", methodName, finish));
        }
        return result;
    }
}
