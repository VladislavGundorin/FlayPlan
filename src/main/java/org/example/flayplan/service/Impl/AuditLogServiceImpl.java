package org.example.flayplan.service.Impl;

import org.example.flayplan.model.AuditLog;
import org.example.flayplan.repository.AuditLogRepository;
import org.example.flayplan.service.AuditLogService;
import org.example.flayplan.service.dtos.AuditLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public AuditLogDTO createAuditLog(AuditLogDTO dto) {
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(dto.getAction());
        auditLog.setTimestamp(dto.getTimestamp());
        auditLog.setPerformedBy(dto.getPerformedBy());
        auditLog.setDetails(dto.getDetails());

        auditLog = auditLogRepository.save(auditLog);
        return new AuditLogDTO(auditLog.getId(), auditLog.getAction(), auditLog.getTimestamp(),
                auditLog.getPerformedBy(), auditLog.getDetails());
    }

    @Override
    public AuditLogDTO getAuditLog(UUID id) {
        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Audit Log not found"));

        return new AuditLogDTO(auditLog.getId(), auditLog.getAction(), auditLog.getTimestamp(),
                auditLog.getPerformedBy(), auditLog.getDetails());
    }

    @Override
    public List<AuditLogDTO> getAllAuditLogs() {
        return auditLogRepository.findAll().stream().map(auditLog ->
                        new AuditLogDTO(auditLog.getId(), auditLog.getAction(), auditLog.getTimestamp(),
                                auditLog.getPerformedBy(), auditLog.getDetails()))
                .collect(Collectors.toList());
    }

    @Override
    public AuditLogDTO updateAuditLog(UUID id, AuditLogDTO dto) {
        AuditLog auditLog = auditLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Audit Log not found"));

        auditLog.setAction(dto.getAction());
        auditLog.setTimestamp(dto.getTimestamp());
        auditLog.setPerformedBy(dto.getPerformedBy());
        auditLog.setDetails(dto.getDetails());

        auditLog = auditLogRepository.save(auditLog);
        return new AuditLogDTO(auditLog.getId(), auditLog.getAction(), auditLog.getTimestamp(),
                auditLog.getPerformedBy(), auditLog.getDetails());
    }

    @Override
    public void deleteAuditLog(UUID id) {
        auditLogRepository.deleteById(id);
    }
}