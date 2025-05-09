package com.example.pc120251backendseccion1.repository;

import com.example.pc120251backendseccion1.model.Matrimonio;
import com.example.pc120251backendseccion1.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatrimonioRepository extends JpaRepository<Matrimonio, Long> {

    boolean existsByPersona1AndPersona2(Persona p1, Persona p2);

    Optional<Matrimonio> findByPersona1AndPersona2(Persona p1, Persona p2);

    boolean existsByPersona1OrPersona2(Persona p1, Persona p2);
}
