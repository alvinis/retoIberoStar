package com.jencys.iberostar.app.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SpaceshipDTO {
    @NotNull
    @NotBlank
    @Size(min = 1)
    private String name;
    @NotNull
    @NotBlank
    @Size(min = 1)
    private String series;
    @NotNull
    @NotBlank
    @Size(min = 1)
    private String movie;
}
