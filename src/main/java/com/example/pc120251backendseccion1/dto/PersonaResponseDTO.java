package com.example.pc120251backendseccion1.dto;

import com.example.pc120251backendseccion1.model.EstadoCivil;
import com.example.pc120251backendseccion1.model.Sexo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class PersonaResponseDTO {

    private String dni;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private Sexo sexo;
    private EstadoCivil estadoCivil;
    private String correo;

    private String dniPadre;
    private String dniMadre;
}
