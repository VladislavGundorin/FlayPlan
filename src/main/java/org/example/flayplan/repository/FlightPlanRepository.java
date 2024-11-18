package org.example.flayplan.repository;

import org.example.flayplan.model.FlightPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlightPlanRepository extends JpaRepository<FlightPlan, UUID> {

    FlightPlan findByApprovalStatus_Id(UUID approvalId);

}
