package org.example.flayplan.controller;

import org.example.flayplan.enums.FlightStatus;
import org.example.flayplan.exception.ResourceNotFoundException;
import org.example.flayplan.model.FlightPlan;
import org.example.flayplan.service.FlightPlanService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/api/flight-plans")
public class FlightPlanController {

    private final FlightPlanService flightPlanService;

    public FlightPlanController(FlightPlanService flightPlanService) {
        this.flightPlanService = flightPlanService;
    }

    @GetMapping
    public List<FlightPlan> getAllFlightPlans() {
        List<FlightPlan> flightPlans = flightPlanService.getAllFlightPlans();
        System.out.println("Flight Plans: " + flightPlans);
        return flightPlans;
    }


    @GetMapping("/{id}")
    public EntityModel<FlightPlan> getFlightPlanById(@PathVariable UUID id) {
        Optional<FlightPlan> flightPlanOptional = flightPlanService.getFlightPlanById(id);

        if (!flightPlanOptional.isPresent()) {
            throw new ResourceNotFoundException("Flight Plan not found");
        }

        FlightPlan flightPlan = flightPlanOptional.get();

        EntityModel<FlightPlan> resource = EntityModel.of(flightPlan);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FlightPlanController.class).getFlightPlanById(id)).withSelfRel();
        Link approveLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FlightPlanController.class).approveFlightPlan(id)).withRel("approve");
        Link cancelLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FlightPlanController.class).cancelFlightPlan(id)).withRel("cancel");

        resource.add(selfLink);
        resource.add(approveLink);
        resource.add(cancelLink);

        return resource;
    }

    @PostMapping
    public FlightPlan createFlightPlan(@RequestBody FlightPlan flightPlan) {
        return flightPlanService.saveFlightPlan(flightPlan);
    }

    @PutMapping("/{id}/approve")
    public EntityModel<FlightPlan> approveFlightPlan(@PathVariable UUID id) {
        Optional<FlightPlan> flightPlanOptional = flightPlanService.getFlightPlanById(id);

        if (!flightPlanOptional.isPresent()) {
            throw new ResourceNotFoundException("Flight Plan not found");
        }

        FlightPlan flightPlan = flightPlanOptional.get();
        flightPlan.setStatus(FlightStatus.APPROVED);
        flightPlanService.saveFlightPlan(flightPlan);

        EntityModel<FlightPlan> resource = EntityModel.of(flightPlan);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FlightPlanController.class).getFlightPlanById(id)).withSelfRel();
        resource.add(selfLink);

        return resource;
    }

    @PutMapping("/{id}/cancel")
    public EntityModel<FlightPlan> cancelFlightPlan(@PathVariable UUID id) {
        Optional<FlightPlan> flightPlanOptional = flightPlanService.getFlightPlanById(id);

        if (!flightPlanOptional.isPresent()) {
            throw new ResourceNotFoundException("Flight Plan not found");
        }

        FlightPlan flightPlan = flightPlanOptional.get();
        flightPlan.setStatus(FlightStatus.CANCELLED);
        flightPlanService.saveFlightPlan(flightPlan);

        EntityModel<FlightPlan> resource = EntityModel.of(flightPlan);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FlightPlanController.class).getFlightPlanById(id)).withSelfRel();
        resource.add(selfLink);

        return resource;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlightPlan(@PathVariable UUID id) {
        if (!flightPlanService.getFlightPlanById(id).isPresent()) {
            throw new ResourceNotFoundException("Flight Plan not found");
        }
        flightPlanService.deleteFlightPlan(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/status/{status}")
    public List<FlightPlan> getFlightPlansByStatus(@PathVariable FlightStatus status) {
        return flightPlanService.getFlightPlansByStatus(status);
    }
    @GetMapping("/approved-by-authority")
    public List<FlightPlan> getApprovedFlightPlansByAuthority(@RequestParam String authorityName) {
        return flightPlanService.getApprovedFlightPlansByAuthority(authorityName);
    }
    //http://localhost:8080/api/flight-plans/approved-by-authority?authorityName=FAA
}
