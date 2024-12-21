package org.example.flayplan.service;


import org.example.dtos.AirspaceAuthorityDTO;

import java.util.List;
import java.util.UUID;

public interface AirspaceAuthorityService {
    AirspaceAuthorityDTO createAirspaceAuthority(AirspaceAuthorityDTO dto);
    AirspaceAuthorityDTO getAirspaceAuthorityById(UUID id);
    List<AirspaceAuthorityDTO> getAllAirspaceAuthorities();
    AirspaceAuthorityDTO updateAirspaceAuthority(UUID id, AirspaceAuthorityDTO dto);
    void deleteAirspaceAuthority(UUID id);
    List<AirspaceAuthorityDTO> getAllAirspaceAuthorities(String region);
}
