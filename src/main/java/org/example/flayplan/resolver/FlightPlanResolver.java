package org.example.flayplan.resolver;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import org.example.dtos.FlightPlanDTO;
import org.example.dtos.WaypointDTO;
import org.example.dtos.enums.FlightStatus;
import org.example.flayplan.service.FlightPlanService;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DgsComponent
public class FlightPlanResolver {

    private final FlightPlanService flightPlanService;

    @Autowired
    public FlightPlanResolver(FlightPlanService flightPlanService) {
        this.flightPlanService = flightPlanService;
    }

    @DgsQuery
    public FlightPlanDTO getFlightPlanById(String id) {
        return flightPlanService.getFlightPlan(UUID.fromString(id));
    }

    @DgsQuery
    public List<FlightPlanDTO> getAllFlightPlans() {
        return flightPlanService.getAllFlightPlans();
    }

    @DgsMutation
    public FlightPlanDTO createFlightPlan(String flightNumber, String airline, String pilotId, List<WaypointDTO> route,
                                          int altitude, String  departureTime, String  arrivalTime,
                                          FlightStatus status, List<String> airspaceAuthorityIds, String approvalId) {
        FlightPlanDTO flightPlanDTO = new FlightPlanDTO();
        flightPlanDTO.setFlightNumber(flightNumber);
        flightPlanDTO.setAirline(airline);
        flightPlanDTO.setPilotId(UUID.fromString(pilotId));
        flightPlanDTO.setRoute(route);
        flightPlanDTO.setAltitude(altitude);
        flightPlanDTO.setDepartureTime(departureTime);
        flightPlanDTO.setArrivalTime(arrivalTime);
        flightPlanDTO.setStatus(status);
        flightPlanDTO.setAirspaceAuthorityIds(airspaceAuthorityIds.stream()
                .map(UUID::fromString)
                .collect(Collectors.toList()));
        flightPlanDTO.setApprovalId(UUID.fromString(approvalId));

        return flightPlanService.createFlightPlan(flightPlanDTO);
    }

    @DgsMutation
    public FlightPlanDTO updateFlightPlan(String id, String flightNumber, String airline, String pilotId,
                                          List<WaypointDTO> route, int altitude, String  departureTime,
                                          String  arrivalTime, FlightStatus status, List<String> airspaceAuthorityIds,
                                          String approvalId) {
        FlightPlanDTO flightPlanDTO = new FlightPlanDTO();
        flightPlanDTO.setFlightNumber(flightNumber);
        flightPlanDTO.setAirline(airline);
        flightPlanDTO.setPilotId(UUID.fromString(pilotId));
        flightPlanDTO.setRoute(route);
        flightPlanDTO.setAltitude(altitude);
        flightPlanDTO.setDepartureTime(departureTime);
        flightPlanDTO.setArrivalTime(arrivalTime);
        flightPlanDTO.setStatus(status);
        flightPlanDTO.setAirspaceAuthorityIds(airspaceAuthorityIds.stream()
                .map(UUID::fromString)
                .collect(Collectors.toList()));
        flightPlanDTO.setApprovalId(UUID.fromString(approvalId));

        return flightPlanService.updateFlightPlan(UUID.fromString(id), flightPlanDTO);
    }

    @DgsMutation
    public Boolean deleteFlightPlan(String id) {
        flightPlanService.deleteFlightPlan(UUID.fromString(id));
        return true;
    }
}
