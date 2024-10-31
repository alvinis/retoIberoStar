package com.jencys.iberostar.app.domain.service.impl;

import com.jencys.iberostar.app.data.entity.Spaceship;
import com.jencys.iberostar.app.data.repository.SpaceshipRepository;
import com.jencys.iberostar.app.domain.service.SpaceshipService;
import com.jencys.iberostar.app.presentation.dto.SpaceshipDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpaceshipServiceImplTest {
    SpaceshipRepository repository;
    SpaceshipService service;

    @BeforeEach
    void setUp() {
        repository = mock(SpaceshipRepository.class);
        service = new SpaceshipServiceImpl(repository);
    }

    @Test
    void findAll_ShouldReturnPagedResults() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Spaceship spaceship = new Spaceship("any-name", "any-serie", "any-movie");
        Page<Spaceship> expectedPage = new PageImpl<>(Collections.singletonList(spaceship));
        when(repository.findAll(pageable)).thenReturn(expectedPage);

        // Act
        Page<Spaceship> result = service.findAll(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(spaceship, result.getContent().get(0));
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    void findById_ShouldReturnSpaceship_WhenFound() {
        // Arrange
        Long id = 1L;
        Spaceship expectedSpaceship = new Spaceship("any-name", "any-serie", "any-movie");
        when(repository.findById(id)).thenReturn(Optional.of(expectedSpaceship));

        // Act
        Spaceship result = service.findById(id);

        // Assert
        assertNotNull(result);
        assertEquals("any-name", result.getName());
        assertEquals("any-serie", result.getSeries());
        assertEquals("any-movie", result.getMovie());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void findById_ShouldThrowEntityNotFoundException_WhenNotFound() {
        // Arrange
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
        verify(repository).findById(id);
    }

    @Test
    void findByName_ShouldReturnPagedResults_WhenFound() {
        // Arrange
        String name = "any-name";
        Pageable pageable = PageRequest.of(0, 10);
        Page<Spaceship> expectedPage = new PageImpl<>(Collections.singletonList(new Spaceship("any-name", "any-series", "any-movie")));
        when(repository.findByNameContaining(name, pageable)).thenReturn(expectedPage);

        // Act
        Page<Spaceship> result = service.findByName(name, pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(name, result.getContent().get(0).getName());
        verify(repository, times(1)).findByNameContaining(name, pageable);
    }

    @Test
    void createSpaceship_ShouldReturnSavedSpaceship() {
        // Arrange
        SpaceshipDTO dto = new SpaceshipDTO("any-name", "any-series", "any-movie");
        Spaceship expectedSpaceship = new Spaceship(dto.getName(), dto.getSeries(), dto.getMovie());
        when(repository.save(any(Spaceship.class))).thenReturn(expectedSpaceship);

        // Act
        Spaceship result = service.createSpaceship(dto);

        // Assert
        assertNotNull(result);
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getSeries(), result.getSeries());
        assertEquals(dto.getMovie(), result.getMovie());
        verify(repository, times(1)).save(any(Spaceship.class));
    }

    @Test
    void updateSpaceship_ShouldReturnUpdatedSpaceship_WhenExists() {
        // Arrange
        Long id = 1L;
        SpaceshipDTO dto = new SpaceshipDTO("any-name", "any-series", "any-movie");
        Spaceship expectedSpaceship = new Spaceship(dto.getName(), dto.getSeries(), dto.getMovie());
        expectedSpaceship.setId(id);
        when(repository.existsById(id)).thenReturn(true);
        when(repository.save(any(Spaceship.class))).thenReturn(expectedSpaceship);

        // Act
        Spaceship result = service.updateSpaceship(id, dto);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(dto.getName(), result.getName());
        assertEquals(dto.getSeries(), result.getSeries());
        assertEquals(dto.getMovie(), result.getMovie());
        verify(repository, times(1)).existsById(id);
        verify(repository, times(1)).save(any(Spaceship.class));
    }

    @Test
    void updateSpaceship_ShouldThrowEntityNotFoundException_WhenNotExists() {
        // Arrange
        Long id = 1L;
        SpaceshipDTO dto = new SpaceshipDTO("Falcon", "X-Wing", "Star Wars");
        when(repository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> service.updateSpaceship(id, dto));
        verify(repository).existsById(id);
        verify(repository, never()).save(any(Spaceship.class));
    }

    @Test
    void deleteSpaceship_ShouldDeleteSpaceship_WhenExists() {
        // Arrange
        Long id = 1L;
        when(repository.existsById(id)).thenReturn(true);

        // Act
        service.deleteSpaceship(id);

        // Assert
        verify(repository).existsById(id);
        verify(repository).deleteById(id);
    }

    @Test
    void deleteSpaceship_ShouldThrowEntityNotFoundException_WhenNotExists() {
        // Arrange
        Long id = 1L;
        when(repository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> service.deleteSpaceship(id));
        verify(repository).existsById(id);
        verify(repository, never()).deleteById(id);
    }
}