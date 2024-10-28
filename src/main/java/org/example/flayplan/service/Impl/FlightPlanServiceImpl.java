package org.example.flayplan.service.Impl;

import org.example.flayplan.model.FlightPlan;
import org.example.flayplan.model.Pilot;
import org.example.flayplan.model.AirspaceAuthority;
import org.example.flayplan.model.Approval;
import org.example.flayplan.model.Waypoint;
import org.example.flayplan.repository.FlightPlanRepository;
import org.example.flayplan.repository.PilotRepository;
import org.example.flayplan.repository.AirspaceAuthorityRepository;
import org.example.flayplan.repository.ApprovalRepository;
import org.example.flayplan.service.FlightPlanService;
import org.example.flayplan.service.dtos.FlightPlanDTO;
import org.example.flayplan.service.dtos.WaypointDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FlightPlanServiceImpl implements FlightPlanService {

    @Autowired
    private FlightPlanRepository flightPlanRepository;
    @Autowired
    private PilotRepository pilotRepository;
    @Autowired
    private AirspaceAuthorityRepository airspaceAuthorityRepository;
    @Autowired
    private ApprovalRepository approvalRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FlightPlanDTO createFlightPlan(FlightPlanDTO dto) {
        FlightPlan flightPlan = modelMapper.map(dto, FlightPlan.class);

        Pilot pilot = pilotRepository.findById(dto.getPilotId())
                .orElseThrow(() -> new RuntimeException("Pilot not found with ID: " + dto.getPilotId()));
        flightPlan.setPilot(pilot);

        List<Waypoint> waypoints = dto.getRoute().stream()
                .map(waypointDto -> modelMapper.map(waypointDto, Waypoint.class))
                .collect(Collectors.toList());
        flightPlan.setRoute(waypoints);

        List<AirspaceAuthority> airspaceAuthorities = dto.getAirspaceAuthorityIds().stream()
                .map(authorityId -> airspaceAuthorityRepository.findById(authorityId)
                        .orElseThrow(() -> new RuntimeException("Airspace Authority not found with ID: " + authorityId)))
                .collect(Collectors.toList());
        flightPlan.setAirspaceAuthorities(airspaceAuthorities);

        Approval approvalStatus = approvalRepository.findById(dto.getApprovalId())
                .orElseThrow(() -> new RuntimeException("Approval not found with ID: " + dto.getApprovalId()));
        flightPlan.setApprovalStatus(approvalStatus);

        flightPlan = flightPlanRepository.save(flightPlan);

        FlightPlanDTO resultDto = modelMapper.map(flightPlan, FlightPlanDTO.class);
        resultDto.setAirspaceAuthorityIds(
                flightPlan.getAirspaceAuthorities().stream()
                        .map(AirspaceAuthority::getId)
                        .collect(Collectors.toList())
        );

        return resultDto;
    }

    @Override
    public FlightPlanDTO getFlightPlan(UUID id) {
        FlightPlan flightPlan = flightPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight Plan not found"));

        return convertToDTO(flightPlan);
    }

    @Override
    public List<FlightPlanDTO> getAllFlightPlans() {
        return flightPlanRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FlightPlanDTO updateFlightPlan(UUID id, FlightPlanDTO dto) {
        FlightPlan flightPlan = flightPlanRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight Plan not found"));

        Pilot pilot = pilotRepository.findById(dto.getPilotId())
                .orElseThrow(() -> new RuntimeException("Pilot not found"));

        List<Waypoint> waypoints = dto.getRoute().stream()
                .map(waypointDto -> new Waypoint(waypointDto.getLatitude(), waypointDto.getLongitude(), waypointDto.getAltitude()))
                .collect(Collectors.toList());

        List<AirspaceAuthority> airspaceAuthorities = dto.getAirspaceAuthorityIds().stream()
                .map(authorityId -> airspaceAuthorityRepository.findById(authorityId)
                        .orElseThrow(() -> new RuntimeException("Airspace Authority not found")))
                .collect(Collectors.toList());

        Approval approvalStatus = approvalRepository.findById(dto.getApprovalId())
                .orElseThrow(() -> new RuntimeException("Approval not found"));

        flightPlan.setFlightNumber(dto.getFlightNumber());
        flightPlan.setAirline(dto.getAirline());
        flightPlan.setPilot(pilot);
        flightPlan.setRoute(waypoints);
        flightPlan.setAltitude(dto.getAltitude());
        flightPlan.setDepartureTime(dto.getDepartureTime());
        flightPlan.setArrivalTime(dto.getArrivalTime());
        flightPlan.setStatus(dto.getStatus());
        flightPlan.setAirspaceAuthorities(airspaceAuthorities);
        flightPlan.setApprovalStatus(approvalStatus);

        flightPlan = flightPlanRepository.save(flightPlan);
        return convertToDTO(flightPlan);
    }

    @Override
    public void deleteFlightPlan(UUID id) {
        flightPlanRepository.deleteById(id);
    }

    private FlightPlanDTO convertToDTO(FlightPlan flightPlan) {
        List<WaypointDTO> waypointDTOs = flightPlan.getRoute().stream()
                .map(waypoint -> new WaypointDTO(waypoint.getId(), waypoint.getLatitude(), waypoint.getLongitude(), waypoint.getAltitude()))
                .collect(Collectors.toList());

        List<UUID> airspaceAuthorityIds = flightPlan.getAirspaceAuthorities().stream()
                .map(AirspaceAuthority::getId)
                .collect(Collectors.toList());

        UUID approvalId = flightPlan.getApprovalStatus() != null ? flightPlan.getApprovalStatus().getId() : null;

        return new FlightPlanDTO(flightPlan.getId(), flightPlan.getFlightNumber(), flightPlan.getAirline(),
                flightPlan.getPilot().getId(), waypointDTOs, flightPlan.getAltitude(),
                flightPlan.getDepartureTime(), flightPlan.getArrivalTime(), flightPlan.getStatus(),
                airspaceAuthorityIds, approvalId);
    }

}
