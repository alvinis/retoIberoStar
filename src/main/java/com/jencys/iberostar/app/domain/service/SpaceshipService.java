package com.jencys.iberostar.app.domain.service;

import com.jencys.iberostar.app.data.entity.Spaceship;
import com.jencys.iberostar.app.presentation.dto.SpaceshipDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SpaceshipService {
    Page<Spaceship> findAll(Pageable pageable);
    Spaceship findById(Long id);
    Page<Spaceship> findByName(String name, Pageable pageable);
    Spaceship createSpaceship(SpaceshipDTO spaceship);
    Spaceship updateSpaceship(Long id, SpaceshipDTO spaceship);
    void deleteSpaceship(Long id);
}
