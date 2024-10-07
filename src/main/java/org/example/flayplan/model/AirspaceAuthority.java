package org.example.flayplan.model;

import java.util.UUID;
import jakarta.persistence.*;

@Entity
public class AirspaceAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String region;
    private String contactInfo;

    public AirspaceAuthority() {
    }

    public AirspaceAuthority(UUID id, String name, String region, String contactInfo) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.contactInfo = contactInfo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}
