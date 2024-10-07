package org.example.flayplan.service;

import org.example.flayplan.model.AuditLog;
import org.example.flayplan.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditLogService {
    private final AuditLogRepository repository;

    public AuditLogService(AuditLogRepository repository) {
        this.repository = repository;
    }

    public List<AuditLog> getLogsByTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        return repository.findByTimestampBetween(startTime, endTime);
    }
}
