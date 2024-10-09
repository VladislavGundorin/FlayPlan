package org.example.flayplan.service;

import org.example.flayplan.service.dtos.AuditLogDTO;

import java.util.List;
import java.util.UUID;

public interface AuditLogService {
    AuditLogDTO createAuditLog(AuditLogDTO dto);
    AuditLogDTO getAuditLog(UUID id);
    List<AuditLogDTO> getAllAuditLogs();
    AuditLogDTO updateAuditLog(UUID id, AuditLogDTO dto);
    void deleteAuditLog(UUID id);
}
