package org.example.flayplan.resolver;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import org.example.flayplan.service.PilotService;
import org.example.flayplan.service.dtos.PilotDTO;
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
        pilotDTO.setContactInfo(contactInfo); // Добавляем контактную информацию
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
            pilotDTO.setContactInfo(contactInfo); // Обновляем контактную информацию
        }
        return pilotService.updatePilot(UUID.fromString(id), pilotDTO); // Используем updatePilot
    }

    @DgsMutation
    public Boolean deletePilot(String id) {
        pilotService.deletePilot(UUID.fromString(id)); // Исправляем на deletePilot
        return true;
    }
}
