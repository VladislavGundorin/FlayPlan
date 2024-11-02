package org.example.flayplan.controller;

import org.example.flayplan.service.AirspaceAuthorityService;
import org.example.flayplan.service.dtos.AirspaceAuthorityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/airspace-authority")
public class AirspaceAuthorityController {
    @Autowired
    private AirspaceAuthorityService airspaceAuthorityService;

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<AirspaceAuthorityDTO>> getAirspaceAuthority(@PathVariable UUID id) {
        AirspaceAuthorityDTO airspaceAuthorityDTO = airspaceAuthorityService.getAirspaceAuthorityById(id);

        if (airspaceAuthorityDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        EntityModel<AirspaceAuthorityDTO> resource = EntityModel.of(airspaceAuthorityDTO);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AirspaceAuthorityController.class).getAirspaceAuthority(id)).withSelfRel();
        Link allAuthoritiesLink = WebMvcLinkBuilder.linkTo(AirspaceAuthorityController.class).withRel("allAuthorities");
        Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AirspaceAuthorityController.class).updateAirspaceAuthority(id, airspaceAuthorityDTO)).withRel("update");

        resource.add(selfLink, allAuthoritiesLink, updateLink);
        return ResponseEntity.ok(resource);
    }
    @PostMapping("/{id}")
    public ResponseEntity<EntityModel<AirspaceAuthorityDTO>> createAirspaceAuthority(@RequestBody AirspaceAuthorityDTO airspaceAuthorityDTO) {
        AirspaceAuthorityDTO createdAirspaceAuthority = airspaceAuthorityService.createAirspaceAuthority(airspaceAuthorityDTO);

        EntityModel<AirspaceAuthorityDTO> resource = EntityModel.of(createdAirspaceAuthority);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AirspaceAuthorityController.class).getAirspaceAuthority(createdAirspaceAuthority.getId())).withSelfRel();
        resource.add(selfLink);
        return ResponseEntity.status(HttpStatus.CREATED).body(resource);
    }
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<AirspaceAuthorityDTO>>> getAllAirspaceAuthorities() {
        List<EntityModel<AirspaceAuthorityDTO>> authorities = airspaceAuthorityService.getAllAirspaceAuthorities().stream()
                .map(authority -> EntityModel.of(authority,
                        WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AirspaceAuthorityController.class).getAirspaceAuthority(authority.getId())).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<AirspaceAuthorityDTO>> collectionModel = CollectionModel.of(authorities);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AirspaceAuthorityController.class).getAllAirspaceAuthorities()).withSelfRel();
        collectionModel.add(selfLink);

        return ResponseEntity.ok(collectionModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<AirspaceAuthorityDTO>> updateAirspaceAuthority(@PathVariable UUID id, @RequestBody AirspaceAuthorityDTO airspaceAuthorityDTO) {
        AirspaceAuthorityDTO updatedAirspaceAuthority = airspaceAuthorityService.updateAirspaceAuthority(id, airspaceAuthorityDTO);

        if (updatedAirspaceAuthority == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        EntityModel<AirspaceAuthorityDTO> resource = EntityModel.of(updatedAirspaceAuthority);
        Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AirspaceAuthorityController.class).getAirspaceAuthority(id)).withSelfRel();
        Link allAuthoritiesLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AirspaceAuthorityController.class).getAllAirspaceAuthorities()).withRel("allAuthorities");

        resource.add(selfLink, allAuthoritiesLink);
        return ResponseEntity.ok(resource);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAirspaceAuthority(@PathVariable UUID id) {
        airspaceAuthorityService.deleteAirspaceAuthority(id);
        return ResponseEntity.noContent().build();
    }
}
