package org.example.flayplan.repository;

import org.example.flayplan.model.AirspaceAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AirspaceAuthorityRepository extends JpaRepository<AirspaceAuthority, UUID> {
}
