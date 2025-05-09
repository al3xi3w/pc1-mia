package com.example.pc120251backendseccion1.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "personas")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Persona {

    @Id
    @Column(nullable = false, unique = true, length = 8)
    private String dni;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCivil estadoCivil;

    @Column(nullable = false, unique = true)
    private String correo;

    @ManyToOne
    @JoinColumn(name = "dni_padre")
    private Persona padre;

    @ManyToOne
    @JoinColumn(name = "dni_madre")
    private Persona madre;
}
