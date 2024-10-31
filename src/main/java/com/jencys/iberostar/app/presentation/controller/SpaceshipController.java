package com.jencys.iberostar.app.presentation.controller;

import com.jencys.iberostar.app.data.entity.Spaceship;
import com.jencys.iberostar.app.domain.service.SpaceshipService;
import com.jencys.iberostar.app.exception.MissingParameterException;
import com.jencys.iberostar.app.presentation.dto.SpaceshipDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/spaceships")
@RequiredArgsConstructor
public class SpaceshipController {
    private final SpaceshipService service;

    @GetMapping
    public HttpEntity<PagedModel<EntityModel<Spaceship>>> getAllSpaceships(Pageable pageable, PagedResourcesAssembler<Spaceship> assembler) {
        Page<Spaceship> all = service.findAll(pageable);
        return ResponseEntity.ok(assembler.toModel(all));
    }

    @GetMapping("/{id}")
    public Spaceship getSpaceshipById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/search")
    public HttpEntity<PagedModel<EntityModel<Spaceship>>> searchSpaceships(@RequestParam String name, Pageable pageable, PagedResourcesAssembler<Spaceship> assembler) {
        if (name == null || name.trim().isEmpty()) {
            throw new MissingParameterException("name");
        }
        Page<Spaceship> all = service.findByName(name, pageable);
        return ResponseEntity.ok(assembler.toModel(all));
    }

    @PostMapping
    public Spaceship createSpaceship(@RequestBody @Valid SpaceshipDTO spaceshipDTO) {
        return service.createSpaceship(spaceshipDTO);
    }

    @PutMapping("/{id}")
    public Spaceship updateSpaceship(@PathVariable Long id, @RequestBody @Valid SpaceshipDTO spaceship) {
        return service.updateSpaceship(id, spaceship);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpaceship(@PathVariable Long id) {
        service.deleteSpaceship(id);
        return ResponseEntity.noContent().build();
    }
}
