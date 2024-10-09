package org.example.flayplan.service.dtos;

import org.example.flayplan.enums.ApprovalStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class ApprovalDTO {
    private UUID id;
    private UUID authorityId;
    private ApprovalStatus status;
    private String approvedBy;
    private LocalDateTime approvedAt;
    private String comments;

    public ApprovalDTO(UUID id, UUID authorityId, ApprovalStatus status, String approvedBy, LocalDateTime approvedAt, String comments) {
        this.id = id;
        this.authorityId = authorityId;
        this.status = status;
        this.approvedBy = approvedBy;
        this.approvedAt = approvedAt;
        this.comments = comments;
    }

    public ApprovalDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(UUID authorityId) {
        this.authorityId = authorityId;
    }

    public ApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(ApprovalStatus status) {
        this.status = status;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public LocalDateTime getApprovedAt() {
        return approvedAt;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
