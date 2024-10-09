package org.example.flayplan.service.Impl;


import org.example.flayplan.model.AirspaceAuthority;
import org.example.flayplan.repository.AirspaceAuthorityRepository;
import org.example.flayplan.service.AirspaceAuthorityService;
import org.example.flayplan.service.dtos.AirspaceAuthorityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AirspaceAuthorityServiceImpl implements AirspaceAuthorityService {

    private final AirspaceAuthorityRepository repository;

    @Autowired
    public AirspaceAuthorityServiceImpl(AirspaceAuthorityRepository repository) {
        this.repository = repository;
    }

    @Override
    public AirspaceAuthorityDTO createAirspaceAuthority(AirspaceAuthorityDTO dto) {
        AirspaceAuthority authority = new AirspaceAuthority(dto.getId(), dto.getName(), dto.getRegion(), dto.getContactInfo());
        AirspaceAuthority savedAuthority = repository.save(authority);
        return new AirspaceAuthorityDTO(savedAuthority.getId(), savedAuthority.getName(), savedAuthority.getRegion(), savedAuthority.getContactInfo());
    }

    @Override
    public AirspaceAuthorityDTO getAirspaceAuthorityById(UUID id) {
        Optional<AirspaceAuthority> authority = repository.findById(id);
        return authority.map(a -> new AirspaceAuthorityDTO(a.getId(), a.getName(), a.getRegion(), a.getContactInfo())).orElse(null);
    }

    @Override
    public List<AirspaceAuthorityDTO> getAllAirspaceAuthorities() {
        return repository.findAll().stream()
                .map(a -> new AirspaceAuthorityDTO(a.getId(), a.getName(), a.getRegion(), a.getContactInfo()))
                .collect(Collectors.toList());
    }

    @Override
    public AirspaceAuthorityDTO updateAirspaceAuthority(UUID id, AirspaceAuthorityDTO dto) {
        if (!repository.existsById(id)) {
            return null;
        }
        AirspaceAuthority authority = new AirspaceAuthority(id, dto.getName(), dto.getRegion(), dto.getContactInfo());
        AirspaceAuthority updatedAuthority = repository.save(authority);
        return new AirspaceAuthorityDTO(updatedAuthority.getId(), updatedAuthority.getName(), updatedAuthority.getRegion(), updatedAuthority.getContactInfo());
    }

    @Override
    public void deleteAirspaceAuthority(UUID id) {
        repository.deleteById(id);
    }
}