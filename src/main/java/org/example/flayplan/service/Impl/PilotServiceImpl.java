package org.example.flayplan.service.Impl;

import org.example.dtos.PilotDTO;
import org.example.flayplan.model.Pilot;
import org.example.flayplan.repository.PilotRepository;
import org.example.flayplan.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PilotServiceImpl implements PilotService {

    @Autowired
    private PilotRepository pilotRepository;

    @Override
    public PilotDTO createPilot(PilotDTO dto) {
        Pilot pilot = new Pilot();
        pilot.setName(dto.getName());
        pilot.setLicenseNumber(dto.getLicenseNumber());
        pilot.setContactInfo(dto.getContactInfo());

        pilot = pilotRepository.save(pilot);
        return new PilotDTO(pilot.getId(), pilot.getName(), pilot.getLicenseNumber(), pilot.getContactInfo());
    }

    @Override
    public PilotDTO getPilot(UUID id) {
        Pilot pilot = pilotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pilot not found"));

        return new PilotDTO(pilot.getId(), pilot.getName(), pilot.getLicenseNumber(), pilot.getContactInfo());
    }

    @Override
    public List<PilotDTO> getAllPilots() {
        return pilotRepository.findAll().stream().map(pilot ->
                        new PilotDTO(pilot.getId(), pilot.getName(), pilot.getLicenseNumber(), pilot.getContactInfo()))
                .collect(Collectors.toList());
    }

    @Override
    public PilotDTO updatePilot(UUID id, PilotDTO dto) {
        Pilot pilot = pilotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pilot not found"));

        pilot.setName(dto.getName());
        pilot.setLicenseNumber(dto.getLicenseNumber());
        pilot.setContactInfo(dto.getContactInfo());

        pilot = pilotRepository.save(pilot);
        return new PilotDTO(pilot.getId(), pilot.getName(), pilot.getLicenseNumber(), pilot.getContactInfo());
    }

    @Override
    public void deletePilot(UUID id) {
        pilotRepository.deleteById(id);
    }
}