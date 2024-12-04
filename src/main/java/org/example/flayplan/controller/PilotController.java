package org.example.flayplan.controller;

import org.example.controllers.PilotApi;
import org.example.dtos.PilotDTO;
import org.example.flayplan.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/pilots")
public class PilotController  implements PilotApi {

    @Autowired
    private PilotService pilotService;

    @PostMapping
    public ResponseEntity<EntityModel<PilotDTO>> createPilot(@RequestBody PilotDTO pilotDTO) {
        PilotDTO createdPilot = pilotService.createPilot(pilotDTO);
        EntityModel<PilotDTO> resource = EntityModel.of(createdPilot);
        Link selfLink = linkTo(methodOn(PilotController.class).getPilot(createdPilot.getId())).withSelfRel();
        resource.add(selfLink);
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PilotDTO>> getPilot(@PathVariable UUID id) {
        PilotDTO pilot = pilotService.getPilot(id);

        if (pilot == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        EntityModel<PilotDTO> resource = EntityModel.of(pilot,
                linkTo(methodOn(PilotController.class).getPilot(id)).withSelfRel(),
                linkTo(methodOn(PilotController.class).updatePilot(id, null)).withRel("update"),
                linkTo(methodOn(PilotController.class).deletePilot(id)).withRel("delete"),
                linkTo(PilotController.class).withRel("allPilots"));

        return ResponseEntity.ok(resource);
    }


    @GetMapping
    public ResponseEntity<RepresentationModel<?>> getAllPilots() {
        List<PilotDTO> pilots = pilotService.getAllPilots();
        RepresentationModel<?> resources = new RepresentationModel<>();

        pilots.forEach(pilot -> {
            Link selfLink = linkTo(methodOn(PilotController.class).getPilot(pilot.getId())).withSelfRel();
            resources.add(selfLink);
        });

        Link selfLink = linkTo(PilotController.class).withSelfRel();
        resources.add(selfLink);

        return ResponseEntity.ok(resources);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<PilotDTO>> updatePilot(@PathVariable UUID id, @RequestBody PilotDTO pilotDTO) {
        PilotDTO updatedPilot = pilotService.updatePilot(id, pilotDTO);
        EntityModel<PilotDTO> resource = EntityModel.of(updatedPilot);
        Link selfLink = linkTo(methodOn(PilotController.class).getPilot(updatedPilot.getId())).withSelfRel();
        resource.add(selfLink);
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePilot(@PathVariable UUID id) {
        pilotService.deletePilot(id);
        return ResponseEntity.noContent().build();
    }
}
