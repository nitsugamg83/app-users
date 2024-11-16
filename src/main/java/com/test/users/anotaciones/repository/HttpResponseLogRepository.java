package com.test.users.anotaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.users.anotaciones.entity.HttpResponseLog;

public interface HttpResponseLogRepository extends JpaRepository<HttpResponseLog, Long> {
}
