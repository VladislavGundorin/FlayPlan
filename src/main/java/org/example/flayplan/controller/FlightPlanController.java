package org.example.flayplan.controller;

import org.example.flayplan.service.FlightPlanService;
import org.example.flayplan.service.dtos.FlightPlanDTO;
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
@RequestMapping("/api/flight-plans")
public class FlightPlanController {

    @Autowired
    private FlightPlanService flightPlanService;


    @PostMapping
    public ResponseEntity<EntityModel<FlightPlanDTO>> createFlightPlan(@RequestBody FlightPlanDTO flightPlanDTO) {
        FlightPlanDTO createdFlightPlan = flightPlanService.createFlightPlan(flightPlanDTO);

        EntityModel<FlightPlanDTO> resource = EntityModel.of(createdFlightPlan);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FlightPlanController.class).getFlightPlan(createdFlightPlan.getId())).withSelfRel();
        resource.add(selfLink);

        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<FlightPlanDTO>> getFlightPlan(@PathVariable UUID id) {
        FlightPlanDTO flightPlan = flightPlanService.getFlightPlan(id);

        EntityModel<FlightPlanDTO> resource = EntityModel.of(flightPlan);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FlightPlanController.class).getFlightPlan(id)).withSelfRel();
        Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FlightPlanController.class).updateFlightPlan(id, null)).withRel("update");
        Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FlightPlanController.class).deleteFlightPlan(id)).withRel("delete");
        Link allLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FlightPlanController.class).getAllFlightPlans()).withSelfRel();

        resource.add(selfLink, updateLink, deleteLink, allLink);

        return ResponseEntity.ok(resource);
    }

    @GetMapping
    public ResponseEntity<RepresentationModel<?>> getAllFlightPlans() {
        List<FlightPlanDTO> flightPlans = flightPlanService.getAllFlightPlans();
        RepresentationModel<?> resources = new RepresentationModel<>();

        flightPlans.forEach(flightPlan -> {
            Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FlightPlanController.class).getFlightPlan(flightPlan.getId())).withSelfRel();
            resources.add(selfLink);
        });

        return ResponseEntity.ok(resources);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<FlightPlanDTO>> updateFlightPlan(@PathVariable UUID id, @RequestBody FlightPlanDTO flightPlanDTO) {
        FlightPlanDTO updatedFlightPlan = flightPlanService.updateFlightPlan(id, flightPlanDTO);

        EntityModel<FlightPlanDTO> resource = EntityModel.of(updatedFlightPlan);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FlightPlanController.class).getFlightPlan(updatedFlightPlan.getId())).withSelfRel();
        resource.add(selfLink);

        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlightPlan(@PathVariable UUID id) {
        flightPlanService.deleteFlightPlan(id);
        return ResponseEntity.noContent().build();
    }
}
