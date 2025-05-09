package com.example.pc120251backendseccion1.repository;

import com.example.pc120251backendseccion1.model.Persona;
import com.example.pc120251backendseccion1.model.EstadoCivil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, String> {

    Optional<Persona> findByDni(String dni);

    List<Persona> findByNombresContainingIgnoreCase(String nombre);

    List<Persona> findByEstadoCivil(EstadoCivil estadoCivil);
}
