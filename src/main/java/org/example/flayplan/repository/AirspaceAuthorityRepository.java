package org.example.flayplan.repository;

import org.example.flayplan.model.AirspaceAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AirspaceAuthorityRepository extends JpaRepository<AirspaceAuthority, UUID> {
}
