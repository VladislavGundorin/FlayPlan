package org.example.flayplan.service.Impl;

import org.example.dtos.ApprovalDTO;
import org.example.dtos.enums.ApprovalStatus;
import org.example.dtos.enums.FlightStatus;
import org.example.flayplan.model.Approval;
import org.example.flayplan.model.AirspaceAuthority;
import org.example.flayplan.model.FlightPlan;
import org.example.flayplan.repository.ApprovalRepository;
import org.example.flayplan.repository.AirspaceAuthorityRepository;
import org.example.flayplan.repository.FlightPlanRepository;
import org.example.flayplan.service.ApprovalService;
import org.example.flayplan.service.AuditLogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ApprovalServiceImpl implements ApprovalService {

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private AirspaceAuthorityRepository airspaceAuthorityRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private FlightPlanRepository flightPlanRepository;

    @Override
    public ApprovalDTO createApproval(ApprovalDTO dto) {
        AirspaceAuthority authority = airspaceAuthorityRepository.findById(dto.getAuthorityId())
                .orElseThrow(() -> new RuntimeException("Airspace Authority not found with ID: " + dto.getAuthorityId()));

        Approval approval = new Approval(
                authority,
                dto.getStatus(),
                dto.getApprovedBy(),
                dto.getApprovedAt(),
                dto.getComments()
        );
        Approval savedApproval = approvalRepository.save(approval);
        return modelMapper.map(savedApproval, ApprovalDTO.class);
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

    @Override
    public ApprovalDTO updateApprovalStatus(ApprovalDTO approvalDTO) {

        Approval approval = approvalRepository.findById(approvalDTO.getId())
                .orElseThrow(() -> new RuntimeException("Approval not found"));

        approval.setStatus(approvalDTO.getStatus());
        approval.setApprovedBy(approvalDTO.getApprovedBy());
        approval.setApprovedAt(approvalDTO.getApprovedAt());
        approval.setComments(approvalDTO.getComments());

        auditLogService.logEvent(
                "Approval Status Updated",
                approvalDTO.getApprovedBy(),
                "Approval ID: " + approvalDTO.getId() + ", Status: " + approvalDTO.getStatus()
        );

        approval = approvalRepository.save(approval);

        FlightPlan flightPlan = flightPlanRepository.findByApprovalStatus_Id(approval.getId());
        if (flightPlan != null) {
            FlightStatus flightStatus = mapApprovalStatusToFlightStatus(approvalDTO.getStatus());
            flightPlan.setStatus(flightStatus);
            flightPlanRepository.save(flightPlan);
        }

        return new ApprovalDTO(
                approval.getId(),
                approval.getAuthority().getId(),
                approval.getStatus(),
                approval.getApprovedBy(),
                approval.getApprovedAt(),
                approval.getComments()
        );
    }

    private FlightStatus mapApprovalStatusToFlightStatus(ApprovalStatus approvalStatus) {
        switch (approvalStatus) {
            case APPROVED:
                return FlightStatus.APPROVED;
            case DENIED:
                return FlightStatus.DENIED;
            case PENDING:
                return FlightStatus.PENDING;
            default:
                throw new IllegalArgumentException("Unknown ApprovalStatus: " + approvalStatus);
        }
    }

}