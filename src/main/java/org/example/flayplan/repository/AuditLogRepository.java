package org.example.flayplan.repository;

import org.example.flayplan.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {
    List<AuditLog> findByTimestampBetween(LocalDateTime startTime, LocalDateTime endTime);
}
