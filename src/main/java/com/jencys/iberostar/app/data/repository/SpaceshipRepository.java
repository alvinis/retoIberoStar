package com.jencys.iberostar.app.data.repository;

import com.jencys.iberostar.app.data.entity.Spaceship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SpaceshipRepository extends JpaRepository<Spaceship, Long> {
    Page<Spaceship> findByNameContaining(String name, Pageable pageable);
}
