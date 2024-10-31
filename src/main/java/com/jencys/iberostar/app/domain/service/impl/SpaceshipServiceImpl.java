package com.jencys.iberostar.app.domain.service.impl;

import com.jencys.iberostar.app.data.entity.Spaceship;
import com.jencys.iberostar.app.data.repository.SpaceshipRepository;
import com.jencys.iberostar.app.domain.service.SpaceshipService;
import com.jencys.iberostar.app.presentation.dto.SpaceshipDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.jencys.iberostar.app.config.CacheConfig;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpaceshipServiceImpl implements SpaceshipService {

    private final SpaceshipRepository spaceshipRepository;

    @Override
    @Cacheable(value = CacheConfig.SPACESHIP_INFO_CACHE, unless = "#result = null")
    public Page<Spaceship> findAll(Pageable pageable) {
        log.info("Fetching all spaceship from database");
        return spaceshipRepository.findAll(pageable);
    }

    @Override
    @Cacheable(value = CacheConfig.SPACESHIP_INFO_CACHE, unless = "#result = null")
    public Spaceship findById(Long id) {
        log.info("Fetching spaceship from database for id: {}", id);
        Optional<Spaceship> spaceship = spaceshipRepository.findById(id);
        if (spaceship.isEmpty()){
            throw new EntityNotFoundException();
        }

        return spaceship.get();
    }

    @Override
    @Cacheable(value = CacheConfig.SPACESHIP_INFO_CACHE, unless = "#result = null")
    public Page<Spaceship> findByName(String name, Pageable pageable) {
        log.info("Fetching spaceship from database for name {}", name);
        return spaceshipRepository.findByNameContaining(name, pageable);
    }

    @Override
    @Transactional
    public Spaceship createSpaceship(SpaceshipDTO spaceship) {
        log.info("Creating new spaceship");
        return spaceshipRepository.save(toEntity(spaceship));
    }

    @Override
    @Transactional
    public Spaceship updateSpaceship(Long id, SpaceshipDTO spaceship) {
        log.info("Updating spaceship for id {}", id);
        boolean exist = spaceshipRepository.existsById(id);
        if (!exist){
            throw new EntityNotFoundException();
        }

        Spaceship entity = toEntity(spaceship);
        entity.setId(id);
        return spaceshipRepository.save(entity);
    }

    @Override
    @Transactional
    public void deleteSpaceship(Long id) {
        log.info("Deleting spaceship for id {}", id);
        boolean exist = spaceshipRepository.existsById(id);
        if (!exist){
            throw new EntityNotFoundException();
        }
        spaceshipRepository.deleteById(id);
    }

    private Spaceship toEntity(SpaceshipDTO dto){
        return new Spaceship(dto.getName(), dto.getSeries(), dto.getMovie());
    }
}
