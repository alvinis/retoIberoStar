package com.jencys.iberostar.app.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity @Table(name = "spaceship")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Spaceship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String series;
    private String movie;

    public Spaceship(String name, String series, String movie) {
        this.name = name;
        this.series = series;
        this.movie = movie;
    }
}



