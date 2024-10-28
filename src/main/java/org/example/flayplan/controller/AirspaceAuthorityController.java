package org.example.flayplan.controller;

import org.example.flayplan.model.AirspaceAuthority;
import org.example.flayplan.service.AirspaceAuthorityService;
import org.example.flayplan.service.dtos.AirspaceAuthorityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

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
//        Link updateLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AirspaceAuthorityController.class).updateAirspaceAuthority(id, null)).withRel("update");
//        Link deleteLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(AirspaceAuthorityController.class).deleteAirspaceAuthority(id)).withRel("delete");

        resource.add(selfLink);
//        resource.add(selfLink, updateLink, deleteLink);

        return ResponseEntity.ok(resource);
    }
}
