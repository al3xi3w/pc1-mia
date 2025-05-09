package com.example.pc120251backendseccion1.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FamiliaResponseDTO {

    private PersonaResponseDTO padre;
    private PersonaResponseDTO madre;

    private PersonaResponseDTO abueloPaterno;
    private PersonaResponseDTO abuelaPaterna;

    private PersonaResponseDTO abueloMaterno;
    private PersonaResponseDTO abuelaMaterna;
}
