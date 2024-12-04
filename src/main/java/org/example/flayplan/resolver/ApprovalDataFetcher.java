package org.example.flayplan.resolver;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import org.example.dtos.ApprovalDTO;

import org.example.dtos.enums.ApprovalStatus;
import org.example.flayplan.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@DgsComponent
public class ApprovalDataFetcher {

    private final ApprovalService approvalService;

    @Autowired
    public ApprovalDataFetcher(ApprovalService approvalService) {
        this.approvalService = approvalService;
    }

    @DgsQuery
    public ApprovalDTO getApprovalById(String id) {
        return approvalService.getApproval(UUID.fromString(id));
    }

    @DgsQuery
    public List<ApprovalDTO> getAllApprovals() {
        return approvalService.getAllApprovals();
    }

    @DgsMutation
    public ApprovalDTO createApproval(String authorityId, String status, String approvedBy, String approvedAt, String comments) {
        ApprovalDTO approvalDTO = new ApprovalDTO();
        approvalDTO.setAuthorityId(UUID.fromString(authorityId));
        approvalDTO.setStatus(ApprovalStatus.valueOf(status));
        approvalDTO.setApprovedBy(approvedBy);
        approvalDTO.setApprovedAt(LocalDateTime.parse(approvedAt));
        approvalDTO.setComments(comments);

        return approvalService.createApproval(approvalDTO);
    }

    @DgsMutation
    public ApprovalDTO updateApproval(String id, String authorityId, String status, String approvedBy, String approvedAt, String comments) {
        ApprovalDTO approvalDTO = new ApprovalDTO();

        if (authorityId != null) {
            approvalDTO.setAuthorityId(UUID.fromString(authorityId));
        }
        if (status != null) {
            approvalDTO.setStatus(ApprovalStatus.valueOf(status));
        }
        if (approvedBy != null) {
            approvalDTO.setApprovedBy(approvedBy);
        }
        if (approvedAt != null) {
            approvalDTO.setApprovedAt(LocalDateTime.parse(approvedAt));
        }
        if (comments != null) {
            approvalDTO.setComments(comments);
        }

        return approvalService.updateApproval(UUID.fromString(id), approvalDTO);
    }

    @DgsMutation
    public Boolean deleteApproval(String id) {
        approvalService.deleteApproval(UUID.fromString(id));
        return true;
    }
}
