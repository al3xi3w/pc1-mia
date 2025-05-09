package com.example.pc120251backendseccion1.dto;

import com.example.pc120251backendseccion1.model.EstadoCivil;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaEstadoCivilDTO {
    @NotNull
    private EstadoCivil nuevoEstadoCivil;
}
