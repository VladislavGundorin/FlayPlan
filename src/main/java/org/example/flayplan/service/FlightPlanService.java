package org.example.flayplan.service;

import org.example.flayplan.enums.ApprovalStatus;
import org.example.flayplan.enums.FlightStatus;
import org.example.flayplan.model.AirspaceAuthority;
import org.example.flayplan.model.FlightPlan;
import org.example.flayplan.repository.FlightPlanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FlightPlanService {

    private final FlightPlanRepository flightPlanRepository;

    public FlightPlanService(FlightPlanRepository flightPlanRepository) {
        this.flightPlanRepository = flightPlanRepository;
    }

    public List<FlightPlan> getAllFlightPlans() {
        return flightPlanRepository.findAll();
    }

    public Optional<FlightPlan> getFlightPlanById(UUID id) {
        return flightPlanRepository.findById(id);
    }

    public FlightPlan saveFlightPlan(FlightPlan flightPlan) {
        return flightPlanRepository.save(flightPlan);
    }
    public void deleteFlightPlan(UUID id) {
        flightPlanRepository.deleteById(id);
    }
    public List<FlightPlan> getFlightPlansByStatus(FlightStatus status) {
        return flightPlanRepository.findByStatus(status);
    }
    public List<FlightPlan> getApprovedFlightPlansByAuthority(String authorityName) {
        return flightPlanRepository.findByApprovalStatus_StatusAndApprovalStatus_Authority_Name(ApprovalStatus.APPROVED, authorityName);
    }
}
