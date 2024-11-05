package org.example.flayplan.service;

import org.example.flayplan.model.AuditLog;
import org.example.flayplan.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditLogService {
    @Autowired
    private AuditLogRepository auditLogRepository;

    public void logEvent(String action, String performedBy, String details) {
        AuditLog log = new AuditLog(action, LocalDateTime.now(), performedBy, details);
        auditLogRepository.save(log);
    }
}
