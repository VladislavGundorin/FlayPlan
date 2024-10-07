package org.example.flayplan.model;

import jakarta.persistence.*;
import org.example.flayplan.enums.FlightStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class FlightPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String flightNumber;
    private String airline;

    @ManyToOne
    @JoinColumn(name = "pilot_id", nullable = false)
    private Pilot pilot;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_plan_id")
    private List<Waypoint> route;

    private int altitude;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    @ManyToMany
    @JoinTable(
            name = "flightplan_authority",
            joinColumns = @JoinColumn(name = "flight_plan_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private List<AirspaceAuthority> airspaceAuthorities;

    @ManyToOne
    @JoinColumn(name = "approval_id")
    private Approval approvalStatus;

    public FlightPlan(String flightNumber, String airline, Pilot pilot, List<Waypoint> route, int altitude, LocalDateTime departureTime, LocalDateTime arrivalTime, FlightStatus status, List<AirspaceAuthority> airspaceAuthorities, Approval approvalStatus, LocalDateTime submittedAt) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.pilot = pilot;
        this.route = route;
        this.altitude = altitude;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.status = status;
        this.airspaceAuthorities = airspaceAuthorities;
        this.approvalStatus = approvalStatus;
        this.submittedAt = submittedAt;
    }

    public FlightPlan() {
    }

    private LocalDateTime submittedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public List<Waypoint> getRoute() {
        return route;
    }

    public void setRoute(List<Waypoint> route) {
        this.route = route;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }

    public List<AirspaceAuthority> getAirspaceAuthorities() {
        return airspaceAuthorities;
    }

    public void setAirspaceAuthorities(List<AirspaceAuthority> airspaceAuthorities) {
        this.airspaceAuthorities = airspaceAuthorities;
    }

    public Approval getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(Approval approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }
}