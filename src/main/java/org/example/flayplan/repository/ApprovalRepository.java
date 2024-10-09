package org.example.flayplan.repository;

import org.example.flayplan.model.Approval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ApprovalRepository extends JpaRepository<Approval, UUID> {
//    Optional<Approval> findById(UUID id);
}
