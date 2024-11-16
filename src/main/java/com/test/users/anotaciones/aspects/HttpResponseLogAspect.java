package com.test.users.anotaciones.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.users.anotaciones.LogHttpResponse;
import com.test.users.anotaciones.entity.HttpResponseLog;
import com.test.users.anotaciones.repository.HttpResponseLogRepository;

@Aspect
@Component
public class HttpResponseLogAspect {

    @Autowired
    private HttpResponseLogRepository httpResponseLogRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Around("@annotation(logHttpResponse)")
    public Object logHttpResponse(ProceedingJoinPoint joinPoint, LogHttpResponse logHttpResponse) throws Throwable {
        Object result;
        String responseBody = "";
        int statusCode = 0;

        try {
            result = joinPoint.proceed();
            if (result instanceof ResponseEntity) {
                ResponseEntity<?> responseEntity = (ResponseEntity<?>) result;
                statusCode = responseEntity.getStatusCodeValue();
                //responseBody = objectMapper.writeValueAsString(responseEntity.getBody());
                responseBody="OK";
            } else {
                //responseBody = objectMapper.writeValueAsString(result);
            }
        }catch (ResponseStatusException e) {
            // Captura de errores de Spring Boot, incluido el error 404
            statusCode = e.getStatus().value(); // Extrae el código de estado HTTP
            responseBody = "Error: " + e.getReason(); // Mensaje de error
            throw e; // Re-lanza la excepción para que el controlador maneje el error

        } catch (Exception e) {
            responseBody = "Error: " + e.getMessage();
            statusCode = 500; // Código de error interno
            throw e;
        } finally {
            String className = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();
            String message = logHttpResponse.value();

            HttpResponseLog log = new HttpResponseLog(className, methodName, statusCode, responseBody, message);
            httpResponseLogRepository.save(log);
        }

        return result;
    }
}
