package com.example.pc120251backendseccion1.dto;

import com.example.pc120251backendseccion1.model.Sexo;
import com.example.pc120251backendseccion1.model.EstadoCivil;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaRequestDTO {

    @NotBlank
    @Size(min = 8, max = 8)
    private String dni;

    @NotBlank
    private String nombres;

    @NotBlank
    private String apellidos;

    @NotNull
    private LocalDate fechaNacimiento;

    @NotNull
    private Sexo sexo;

    @NotNull
    private EstadoCivil estadoCivil;

    @NotBlank
    @Email
    private String correo;
}
