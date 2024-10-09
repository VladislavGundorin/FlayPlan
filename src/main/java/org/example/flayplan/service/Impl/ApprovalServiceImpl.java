package org.example.flayplan.service.Impl;

import org.example.flayplan.enums.ApprovalStatus;
import org.example.flayplan.model.Approval;
import org.example.flayplan.model.AirspaceAuthority;
import org.example.flayplan.repository.ApprovalRepository;
import org.example.flayplan.repository.AirspaceAuthorityRepository;
import org.example.flayplan.service.ApprovalService;
import org.example.flayplan.service.dtos.ApprovalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ApprovalServiceImpl implements ApprovalService {

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private AirspaceAuthorityRepository airspaceAuthorityRepository;

    @Override
    public ApprovalDTO createApproval(ApprovalDTO dto) {
        AirspaceAuthority authority = airspaceAuthorityRepository.findById(dto.getAuthorityId())
                .orElseThrow(() -> new RuntimeException("Airspace authority not found"));

        Approval approval = new Approval();
        approval.setAuthority(authority);
        approval.setStatus(dto.getStatus());
        approval.setApprovedBy(dto.getApprovedBy());
        approval.setApprovedAt(dto.getApprovedAt());
        approval.setComments(dto.getComments());

        approval = approvalRepository.save(approval);
        return new ApprovalDTO(approval.getId(), dto.getAuthorityId(), approval.getStatus(),
                approval.getApprovedBy(), approval.getApprovedAt(), approval.getComments());
    }

    @Override
    public ApprovalDTO getApproval(UUID id) {
        Approval approval = approvalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Approval not found"));

        return new ApprovalDTO(approval.getId(), approval.getAuthority().getId(), approval.getStatus(),
                approval.getApprovedBy(), approval.getApprovedAt(), approval.getComments());
    }

    @Override
    public List<ApprovalDTO> getAllApprovals() {
        return approvalRepository.findAll().stream().map(approval ->
                        new ApprovalDTO(approval.getId(), approval.getAuthority().getId(), approval.getStatus(),
                                approval.getApprovedBy(), approval.getApprovedAt(), approval.getComments()))
                .collect(Collectors.toList());
    }

    @Override
    public ApprovalDTO updateApproval(UUID id, ApprovalDTO dto) {
        Approval approval = approvalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Approval not found"));

        AirspaceAuthority authority = airspaceAuthorityRepository.findById(dto.getAuthorityId())
                .orElseThrow(() -> new RuntimeException("Airspace authority not found"));

        approval.setAuthority(authority);
        approval.setStatus(dto.getStatus());
        approval.setApprovedBy(dto.getApprovedBy());
        approval.setApprovedAt(dto.getApprovedAt());
        approval.setComments(dto.getComments());

        approval = approvalRepository.save(approval);
        return new ApprovalDTO(approval.getId(), dto.getAuthorityId(), approval.getStatus(),
                approval.getApprovedBy(), approval.getApprovedAt(), approval.getComments());
    }

    @Override
    public void deleteApproval(UUID id) {
        approvalRepository.deleteById(id);
    }
}