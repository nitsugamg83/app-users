package com.test.users.anotaciones.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.test.users.anotaciones.LogContextInfo;
import com.test.users.anotaciones.entity.LogEntry;
import com.test.users.anotaciones.repository.LogEntryRepository;

@Aspect
@Component
public class LogContextAspect {

    @Autowired
    private LogEntryRepository logEntryRepository;

    @After("@annotation(logContextInfo)")
    public void logContextInfo(JoinPoint joinPoint, LogContextInfo logContextInfo) {
        // Obtener el nombre del usuario actual desde el contexto de seguridad
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Obtener información del método
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String message = logContextInfo.value();

        // Crear y guardar el registro en la base de datos
        LogEntry logEntry = new LogEntry(username, className, methodName, message);
        logEntryRepository.save(logEntry);
    }
}
