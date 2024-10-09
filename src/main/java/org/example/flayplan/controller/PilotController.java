package org.example.flayplan.controller;

import org.example.flayplan.service.PilotService;
import org.example.flayplan.service.dtos.PilotDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/pilots")
public class PilotController {

    @Autowired
    private PilotService pilotService;

    @PostMapping
    public ResponseEntity<EntityModel<PilotDTO>> createPilot(@RequestBody PilotDTO pilotDTO) {
        PilotDTO createdPilot = pilotService.createPilot(pilotDTO);
        EntityModel<PilotDTO> resource = EntityModel.of(createdPilot);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PilotController.class).getPilot(createdPilot.getId())).withSelfRel();
        resource.add(selfLink);
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PilotDTO>> getPilot(@PathVariable UUID id) {
        PilotDTO pilot = pilotService.getPilot(id);

        EntityModel<PilotDTO> resource = EntityModel.of(pilot);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PilotController.class).getPilot(id)).withSelfRel();
        Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PilotController.class).updatePilot(id, null)).withRel("update");
        Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PilotController.class).deletePilot(id)).withRel("delete");

        resource.add(selfLink, updateLink, deleteLink);

        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<RepresentationModel<?>> getAllPilots() {
        List<PilotDTO> pilots = pilotService.getAllPilots();
        RepresentationModel<?> resources = new RepresentationModel<>();

        pilots.forEach(pilot -> {
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PilotController.class).getPilot(pilot.getId())).withSelfRel();
            resources.add(selfLink);
        });

        Link selfLink = WebMvcLinkBuilder.linkTo(PilotController.class).withSelfRel();
        resources.add(selfLink);

        return ResponseEntity.ok(resources);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<PilotDTO>> updatePilot(@PathVariable UUID id, @RequestBody PilotDTO pilotDTO) {
        PilotDTO updatedPilot = pilotService.updatePilot(id, pilotDTO);
        EntityModel<PilotDTO> resource = EntityModel.of(updatedPilot);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PilotController.class).getPilot(updatedPilot.getId())).withSelfRel();
        resource.add(selfLink);
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePilot(@PathVariable UUID id) {
        pilotService.deletePilot(id);
        return ResponseEntity.noContent().build();
    }
}
