package com.test.users.anotaciones.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

@Entity
public class LogEntry {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String className;
    private String methodName;
    private String message;
    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;
	@Override
	public String toString() {
		return "LogEntry [id=" + id + ", username=" + username + ", className=" + className + ", methodName="
				+ methodName + ", message=" + message + ", timestamp=" + timestamp + "]";
	}
	public LogEntry(Long id, String username, String className, String methodName, String message) {
		super();
		this.id = id;
		this.username = username;
		this.className = className;
		this.methodName = methodName;
		this.message = message;
	}
	public LogEntry(String username, String className, String methodName, String message) {
		super();
		this.username = username;
		this.className = className;
		this.methodName = methodName;
		this.message = message;
	}
	@PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }
    
    
    

}
