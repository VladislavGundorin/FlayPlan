package org.example.flayplan.service;


import org.example.dtos.ApprovalDTO;

import java.util.List;
import java.util.UUID;

public interface ApprovalService {
    ApprovalDTO createApproval(ApprovalDTO dto);
    ApprovalDTO getApproval(UUID id);
    List<ApprovalDTO> getAllApprovals();
    ApprovalDTO updateApproval(UUID id, ApprovalDTO dto);
    void deleteApproval(UUID id);
    ApprovalDTO updateApprovalStatus(ApprovalDTO approvalDTO);
}
