package org.example.flayplan.controller;

import org.example.flayplan.service.ApprovalService;
import org.example.flayplan.service.dtos.ApprovalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/approval")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;

    @PostMapping
    public ResponseEntity<EntityModel<ApprovalDTO>> createApproval(@RequestBody ApprovalDTO approvalDTO) {
        ApprovalDTO createdApproval = approvalService.createApproval(approvalDTO);
        EntityModel<ApprovalDTO> resource = EntityModel.of(createdApproval);
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ApprovalDTO>>> getAllApprovals() {
        List<EntityModel<ApprovalDTO>> approvals = approvalService.getAllApprovals().stream()
                .map(approval -> EntityModel.of(approval,
                        linkTo(methodOn(ApprovalController.class).getApproval(approval.getId())).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<ApprovalDTO>> collectionModel = CollectionModel.of(approvals);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ApprovalDTO>> getApproval(@PathVariable UUID id) {
        ApprovalDTO approval = approvalService.getApproval(id);

        if (approval == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        EntityModel<ApprovalDTO> resource = EntityModel.of(approval,
                linkTo(methodOn(ApprovalController.class).getApproval(id)).withSelfRel(),
                linkTo(methodOn(ApprovalController.class).updateApproval(id, null)).withRel("update"),
                linkTo(methodOn(ApprovalController.class).deleteApproval(id)).withRel("delete"),
                linkTo(ApprovalController.class).withRel("allApprovals"));

        return ResponseEntity.ok(resource);
    }


    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ApprovalDTO>> updateApproval(
            @PathVariable UUID id, @RequestBody ApprovalDTO approvalDTO) {
        ApprovalDTO updatedApproval = approvalService.updateApproval(id, approvalDTO);
        EntityModel<ApprovalDTO> resource = EntityModel.of(updatedApproval,
                linkTo(methodOn(ApprovalController.class).getApproval(id)).withSelfRel());
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApproval(@PathVariable UUID id) {
        approvalService.deleteApproval(id);
        return ResponseEntity.noContent().build();
    }
}
