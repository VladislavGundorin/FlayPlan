package org.example.flayplan.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Waypoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private double latitude;
    private double longitude;
    private int altitude;

    public Waypoint(double latitude, double longitude, int altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public Waypoint() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }
}
