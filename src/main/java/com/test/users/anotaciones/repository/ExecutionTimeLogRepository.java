package com.test.users.anotaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.users.anotaciones.entity.ExecutionTimeLog;

public interface ExecutionTimeLogRepository extends JpaRepository<ExecutionTimeLog, Long> {
}
