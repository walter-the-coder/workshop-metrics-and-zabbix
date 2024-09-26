package com.example.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RepositoryExecutionTimeAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryExecutionTimeAspect.class);

    private final MeterRegistry meterRegistry;

    public RepositoryExecutionTimeAspect(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Around("execution(* com.example.repository.*.*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            long endTime = System.nanoTime();

            String methodName = joinPoint.getSignature().getName();
            String className = joinPoint.getTarget().getClass().getSimpleName();

            Timer timer = Timer.builder("databaseQueries")
                    .description("Timer for repository classes")
                    .tag("class", className)
                    .tag("method", methodName)
                    .register(meterRegistry);

            long durationInMilliseconds = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);

            timer.record(durationInMilliseconds, TimeUnit.MILLISECONDS);

            LOGGER.info("Method: {}.{} executed in {} ms", className, methodName, durationInMilliseconds);
        }
    }
}
