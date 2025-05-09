package com.example.pc120251backendseccion1.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaUpdateNombreDTO {

    @NotBlank
    private String nombres;

    @NotBlank
    private String apellidos;
}
