package com.test.users.anotaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.users.anotaciones.entity.LogEntry;

@Repository
public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
	
}
