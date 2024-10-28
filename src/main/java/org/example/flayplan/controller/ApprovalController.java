package org.example.flayplan.controller;

import org.example.flayplan.service.ApprovalService;
import org.example.flayplan.service.dtos.ApprovalDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
