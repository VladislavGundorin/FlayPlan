package org.example.flayplan.conf;

import org.example.flayplan.enums.ApprovalStatus;
import org.example.flayplan.enums.FlightStatus;
import org.example.flayplan.model.*;
import org.example.flayplan.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final FlightPlanRepository flightPlanRepository;
    private final PilotRepository pilotRepository;
    private final AirspaceAuthorityRepository airspaceAuthorityRepository;
    private final ApprovalRepository approvalRepository;

    public DataInitializer(FlightPlanRepository flightPlanRepository,
                           AirspaceAuthorityRepository airspaceAuthorityRepository,
                           ApprovalRepository approvalRepository,
                           PilotRepository pilotRepository) {
        this.flightPlanRepository = flightPlanRepository;
        this.pilotRepository = pilotRepository;
        this.airspaceAuthorityRepository = airspaceAuthorityRepository;
        this.approvalRepository = approvalRepository;
    }

    @Override
    public void run(String... args) {
        Pilot pilot1 = new Pilot();
        pilot1.setName("John Doe");
        pilot1.setLicenseNumber("PILOT-12345");
        pilot1.setContactInfo("john@example.com");
        pilotRepository.save(pilot1);

        Pilot pilot2 = new Pilot();
        pilot2.setName("Jane Smith");
        pilot2.setLicenseNumber("PILOT-67890");
        pilot2.setContactInfo("jane@example.com");
        pilotRepository.save(pilot2);

        AirspaceAuthority authority = new AirspaceAuthority();
        authority.setName("FAA");
        authority.setRegion("USA");
        authority.setContactInfo("contact@faa.gov");
        airspaceAuthorityRepository.save(authority);

        Approval approval = new Approval();
        approval.setAuthority(authority);
        approval.setStatus(ApprovalStatus.PENDING);
        approval.setApprovedBy("Admin");
        approval.setApprovedAt(LocalDateTime.now());
        approval.setComments("Initial approval");
        approvalRepository.save(approval);

        FlightPlan flightPlan1 = new FlightPlan();
        flightPlan1.setFlightNumber("FL123");
        flightPlan1.setAirline("Example Airlines");
        flightPlan1.setPilot(pilot1);
        flightPlan1.setRoute(Arrays.asList(new Waypoint(12.34, 56.78, 30000)));
        flightPlan1.setAltitude(30000);
        flightPlan1.setDepartureTime(LocalDateTime.now().plusHours(1).toString());
        flightPlan1.setArrivalTime(LocalDateTime.now().plusHours(3).toString());
        flightPlan1.setStatus(FlightStatus.PENDING);
        flightPlan1.setAirspaceAuthorities(Arrays.asList(authority));
        flightPlan1.setApprovalStatus(approval);
        flightPlan1.setSubmittedAt(LocalDateTime.now());
        flightPlanRepository.save(flightPlan1);

    }
}