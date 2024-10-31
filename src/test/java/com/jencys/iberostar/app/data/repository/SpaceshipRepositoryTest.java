package com.jencys.iberostar.app.data.repository;

import com.jencys.iberostar.app.data.entity.Spaceship;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SpaceshipRepositoryIntegrationTest {

    @Autowired
    private SpaceshipRepository spaceshipRepository;

    @Test
    void testFindByNameContaining() {
        Spaceship spaceship1 = new Spaceship("any-name", "any-series", "any-movie");
        Spaceship spaceship2 = new Spaceship("any-other", "any-series", "any-movie");


        spaceshipRepository.save(spaceship1);
        spaceshipRepository.save(spaceship2);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("name"));

        Page<Spaceship> page = spaceshipRepository.findByNameContaining("any-", pageable);

        List<Spaceship> result = page.getContent();


        assertEquals(2, result.size());
        assertThat(result).extracting(Spaceship::getName).containsExactly("any-name", "any-other");
    }

    @Test
    void testFindAll() {
        Spaceship spaceship1 = new Spaceship("any-name", "any-series", "any-movie");
        Spaceship spaceship2 = new Spaceship("another-name", "another-series", "another-movie");
        spaceshipRepository.save(spaceship1);
        spaceshipRepository.save(spaceship2);

        List<Spaceship> result = spaceshipRepository.findAll();

        assertThat(result).hasSize(11); //11 por el data.sql de los resources
        assertThat(result).extracting(Spaceship::getName).contains("any-name", "another-name");
    }

    @Test
    public void testFindById() {
        Spaceship spaceship = new Spaceship("unique-name", "unique-series", "unique-movie");
        spaceship = spaceshipRepository.save(spaceship);

        Optional<Spaceship> foundSpaceship = spaceshipRepository.findById(spaceship.getId());

        assertTrue(foundSpaceship.isPresent());
        assertEquals(spaceship.getId(), foundSpaceship.get().getId());
    }

    @Test
    void testSave() {
        Spaceship spaceship = new Spaceship("save-test-name", "save-test-series", "save-test-movie");
        Spaceship savedSpaceship = spaceshipRepository.save(spaceship);

        assertThat(savedSpaceship).isNotNull();
        assertThat(savedSpaceship.getId()).isNotNull();
        assertThat(savedSpaceship.getName()).isEqualTo("save-test-name");
    }

    @Test
    public void testExistsById() {
        Spaceship spaceship = new Spaceship("exists-test-name", "exists-test-series", "exists-test-movie");
        Spaceship savedSpaceship = spaceshipRepository.save(spaceship);

        boolean exists = spaceshipRepository.existsById(savedSpaceship.getId());

        assertTrue(exists);
    }

    @Test
    public void testDeleteById() {
        Spaceship spaceship = new Spaceship("delete-test-name", "delete-test-series", "delete-test-movie");
        spaceship = spaceshipRepository.save(spaceship);

        spaceshipRepository.deleteById(spaceship.getId());

        Optional<Spaceship> foundSpaceship = spaceshipRepository.findById(spaceship.getId());

        assertThat(foundSpaceship).isNotPresent();
    }
}