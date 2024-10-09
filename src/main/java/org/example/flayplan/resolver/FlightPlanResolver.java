package org.example.flayplan.resolver;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import org.example.flayplan.enums.FlightStatus;
import org.example.flayplan.service.FlightPlanService;
import org.example.flayplan.service.dtos.FlightPlanDTO;
import org.example.flayplan.service.dtos.WaypointDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
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
//mutation {
//    updateFlightPlan(
//        id: "10c04135-8b9a-4bba-884d-5452b6e407be",
//        flightNumber: "FL456",
//        airline: "Updated Airline",
//        pilotId: "01fe1bf3-dd0e-4a69-9a14-8b3a609d568a",
//        route: [{ latitude: 34.0522, longitude: -118.2437, altitude: 31000 }],
//        altitude: 31000,
//        departureTime: "2024-10-09T14:30:00",
//        arrivalTime: "2024-10-09T16:30:00",
//        status: PENDING,
//        airspaceAuthorityIds: ["ca1b653e-0ada-4ecf-9d4d-4ec025810873"],
//        approvalId: "772cc641-8281-416e-8c42-be3fa2916472"
//    ) {
//        id
//        flightNumber
//        airline
//        route {
//            latitude
//            longitude
//            altitude
//        }
//        altitude
//        departureTime
//        arrivalTime
//        status
//    }
//}
