package org.example.flayplan.model;

import jakarta.persistence.*;
import org.example.dtos.enums.LogLevel;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String action;
    private LocalDateTime timestamp;
    private String performedBy;
    private String details;
    private LogLevel level;

    public AuditLog(String action, LocalDateTime timestamp, String performedBy, String details,LogLevel level) {
        this.action = action;
        this.timestamp = timestamp;
        this.performedBy = performedBy;
        this.details = details;
        this.level = level;
    }

    public AuditLog() {
    }
    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
