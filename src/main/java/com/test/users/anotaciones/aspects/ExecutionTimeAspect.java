package com.test.users.anotaciones.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.test.users.anotaciones.TimedExecution;
import com.test.users.anotaciones.entity.ExecutionTimeLog;
import com.test.users.anotaciones.repository.ExecutionTimeLogRepository;

@Aspect
@Component
public class ExecutionTimeAspect {

    @Autowired
    private ExecutionTimeLogRepository executionTimeLogRepository;

    @Around("@annotation(timedExecution)")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint, TimedExecution timedExecution) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result;
        try {
            result = joinPoint.proceed(); // Ejecuta el método
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            // Obtiene información del método
            String className = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();
            String message = timedExecution.value();

            // Guarda la información en la base de datos
            ExecutionTimeLog log = new ExecutionTimeLog(className, methodName, executionTime, message);
            executionTimeLogRepository.save(log);
        }

        return result;
    }
}
