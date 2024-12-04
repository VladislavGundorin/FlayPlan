package org.example.flayplan.service;


import org.example.dtos.FlightPlanDTO;

import java.util.List;
import java.util.UUID;

public interface FlightPlanService {
    FlightPlanDTO createFlightPlan(FlightPlanDTO dto);
    FlightPlanDTO getFlightPlan(UUID id);
    List<FlightPlanDTO> getAllFlightPlans();
    FlightPlanDTO updateFlightPlan(UUID id, FlightPlanDTO dto);
    void deleteFlightPlan(UUID id);
}
