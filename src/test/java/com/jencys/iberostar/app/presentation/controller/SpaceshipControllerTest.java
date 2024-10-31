package com.jencys.iberostar.app.presentation.controller;

import com.jencys.iberostar.app.data.entity.Spaceship;
import com.jencys.iberostar.app.domain.service.SpaceshipService;
import com.jencys.iberostar.app.presentation.dto.SpaceshipDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SpaceshipController.class)
class SpaceshipControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpaceshipService spaceshipService;

    @Test
    public void testGetAllSpaceships() throws Exception {
        Spaceship spaceship = new Spaceship(1L, "any-name", "any-series", "any-movie");
        Page<Spaceship> page = new PageImpl<>(Collections.singletonList(spaceship));

        when(spaceshipService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/spaceships"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$._embedded.spaceshipList[0].id").value(spaceship.getId()))
                .andExpect(jsonPath("$._embedded.spaceshipList[0].name").value(spaceship.getName()))
                .andExpect(jsonPath("$._embedded.spaceshipList[0].series").value(spaceship.getSeries()))
                .andExpect(jsonPath("$._embedded.spaceshipList[0].movie").value(spaceship.getMovie()))
                .andExpect(jsonPath("$.page.size").value(1))
                .andExpect(jsonPath("$.page.totalElements").value(1))
                .andExpect(jsonPath("$.page.totalPages").value(1))
                .andExpect(jsonPath("$.page.number").value(0));
    }

    @Test
    public void testGetSpaceshipById() throws Exception {
        Spaceship spaceship = new Spaceship(1L, "any-name", "any-series", "any-movie");
        when(spaceshipService.findById(anyLong())).thenReturn(spaceship);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/spaceships/{id}", spaceship.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(spaceship.getId()))
                .andExpect(jsonPath("$.name").value(spaceship.getName()))
                .andExpect(jsonPath("$.series").value(spaceship.getSeries()))
                .andExpect(jsonPath("$.movie").value(spaceship.getMovie()));
    }

    @Test
    public void testSearchSpaceships() throws Exception {
        Spaceship spaceship = new Spaceship(1L, "any-name", "any-series", "any-movie");
        Page<Spaceship> page = new PageImpl<>(Collections.singletonList(spaceship));

        when(spaceshipService.findByName(any(String.class), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/spaceships/search")
                        .param("name", spaceship.getName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$._embedded.spaceshipList[0].name").value(spaceship.getName()));
    }

    @Test
    public void testSearchSpaceshipsEmptyName() throws Exception {
        Spaceship spaceship = new Spaceship(1L, "any-name", "any-series", "any-movie");
        Page<Spaceship> page = new PageImpl<>(Collections.singletonList(spaceship));

        when(spaceshipService.findByName(any(String.class), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/spaceships/search")
                        .param("name", ""))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message").value("El parámetro 'name' es obligatorio."));
    }

    @Test
    public void testCreateSpaceship() throws Exception {
        SpaceshipDTO spaceshipDTO = new SpaceshipDTO("any-name", "any-series", "any-movie");
        Spaceship savedSpaceship = new Spaceship(1L, "any-name", "any-series", "any-movie");
        when(spaceshipService.createSpaceship(any(SpaceshipDTO.class))).thenReturn(savedSpaceship);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/spaceships")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"any-name\", \"series\": \"any-series\", \"movie\": \"any-movie\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(savedSpaceship.getId()))
                .andExpect(jsonPath("$.name").value(savedSpaceship.getName()))
                .andExpect(jsonPath("$.series").value(savedSpaceship.getSeries()))
                .andExpect(jsonPath("$.movie").value(savedSpaceship.getMovie()));
    }

    @Test
    public void testUpdateSpaceship() throws Exception {
        SpaceshipDTO spaceshipDTO = new SpaceshipDTO("updated-name", "updated-series", "updated-movie");
        Spaceship updatedSpaceship = new Spaceship(1L, "updated-name", "updated-series", "updated-movie");
        when(spaceshipService.updateSpaceship(anyLong(), any(SpaceshipDTO.class))).thenReturn(updatedSpaceship);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/spaceships/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"updated-name\", \"series\": \"updated-series\", \"movie\": \"updated-movie\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(updatedSpaceship.getId()))
                .andExpect(jsonPath("$.name").value(updatedSpaceship.getName()))
                .andExpect(jsonPath("$.series").value(updatedSpaceship.getSeries()))
                .andExpect(jsonPath("$.movie").value(updatedSpaceship.getMovie()));
    }

    @Test
    public void testDeleteSpaceship() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/spaceships/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}