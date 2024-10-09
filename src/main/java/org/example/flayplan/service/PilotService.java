package org.example.flayplan.service;

import org.example.flayplan.service.dtos.PilotDTO;

import java.util.List;
import java.util.UUID;

public interface PilotService {
    PilotDTO createPilot(PilotDTO dto);
    PilotDTO getPilot(UUID id);
    List<PilotDTO> getAllPilots();
    PilotDTO updatePilot(UUID id, PilotDTO dto);
    void deletePilot(UUID id);
}
