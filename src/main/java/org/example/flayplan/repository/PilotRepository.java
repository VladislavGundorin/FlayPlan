package org.example.flayplan.repository;

import org.example.flayplan.model.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PilotRepository extends JpaRepository<Pilot, UUID> {

}
