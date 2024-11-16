package com.test.users.anotaciones.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ExecutionTimeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String className;
    private String methodName;
    private long executionTime; // Tiempo de ejecución en milisegundos
    private LocalDateTime timestamp;
    private String message;

    // Constructor, getters y setters

    public ExecutionTimeLog(String className, String methodName, long executionTime, String message) {
        this.className = className;
        this.methodName = methodName;
        this.executionTime = executionTime;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
