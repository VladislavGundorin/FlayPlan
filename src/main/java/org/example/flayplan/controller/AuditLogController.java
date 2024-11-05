package org.example.flayplan.controller;

import org.example.flayplan.model.AuditLog;
import org.example.flayplan.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @GetMapping
    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }

    @GetMapping("/{id}")
    public AuditLog getLogById(@PathVariable UUID id) {
        return auditLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AuditLog not found with ID: " + id));
    }
}
