package org.example.flayplan.resolver;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import org.example.dtos.PilotDTO;
import org.example.flayplan.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@DgsComponent
public class PilotDataFetcher {

    private final PilotService pilotService;

    @Autowired
    public PilotDataFetcher(PilotService pilotService) {
        this.pilotService = pilotService;
    }

    @DgsQuery
    public PilotDTO getPilotById(String id) {
        return pilotService.getPilot(UUID.fromString(id));
    }

    @DgsQuery
    public List<PilotDTO> getAllPilots() {
        return pilotService.getAllPilots();
    }

    @DgsMutation
    public PilotDTO createPilot(String name, String licenseNumber, String contactInfo) {
        PilotDTO pilotDTO = new PilotDTO();
        pilotDTO.setName(name);
        pilotDTO.setLicenseNumber(licenseNumber);
        pilotDTO.setContactInfo(contactInfo);
        return pilotService.createPilot(pilotDTO);
    }

    @DgsMutation
    public PilotDTO patchPilot(String id, String name, String licenseNumber, String contactInfo) {
        PilotDTO pilotDTO = new PilotDTO();
        if (name != null) {
            pilotDTO.setName(name);
        }
        if (licenseNumber != null) {
            pilotDTO.setLicenseNumber(licenseNumber);
        }
        if (contactInfo != null) {
            pilotDTO.setContactInfo(contactInfo);
        }
        return pilotService.updatePilot(UUID.fromString(id), pilotDTO);
    }

    @DgsMutation
    public Boolean deletePilot(String id) {
        pilotService.deletePilot(UUID.fromString(id));
        return true;
    }
}
