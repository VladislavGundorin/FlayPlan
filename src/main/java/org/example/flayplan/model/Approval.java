package org.example.flayplan.model;

import jakarta.persistence.*;
import org.example.dtos.enums.ApprovalStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Approval {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    private AirspaceAuthority authority;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    private String approvedBy;
    private LocalDateTime approvedAt;
    private String comments;

    public Approval(AirspaceAuthority authority, ApprovalStatus status, String approvedBy, LocalDateTime approvedAt, String comments) {
        this.authority = authority;
        this.status = status;
        this.approvedBy = approvedBy;
        this.approvedAt = approvedAt;
        this.comments = comments;
    }

    public Approval() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AirspaceAuthority getAuthority() {
        return authority;
    }

    public void setAuthority(AirspaceAuthority authority) {
        this.authority = authority;
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
