package org.example.flayplan.repository;

import org.example.flayplan.enums.ApprovalStatus;
import org.example.flayplan.enums.FlightStatus;
import org.example.flayplan.model.AirspaceAuthority;
import org.example.flayplan.model.FlightPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FlightPlanRepository extends JpaRepository<FlightPlan, UUID> {
    List<FlightPlan> findByStatus(FlightStatus status);

    List<FlightPlan> findByApprovalStatus_StatusAndApprovalStatus_Authority_Name(ApprovalStatus status, String authorityName);
}
