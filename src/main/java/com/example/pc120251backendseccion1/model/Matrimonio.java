package com.example.pc120251backendseccion1.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "matrimonios",
        uniqueConstraints = @UniqueConstraint(columnNames = {"dni_persona1", "dni_persona2"})
)
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Matrimonio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "dni_persona1", referencedColumnName = "dni")
    private Persona persona1;

    @ManyToOne(optional = false)
    @JoinColumn(name = "dni_persona2", referencedColumnName = "dni")
    private Persona persona2;

    @Column(nullable = false)
    private LocalDate fechaRegistro;
}
