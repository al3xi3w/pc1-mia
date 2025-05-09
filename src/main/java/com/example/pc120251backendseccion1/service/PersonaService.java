package com.example.pc120251backendseccion1.service;

import com.example.pc120251backendseccion1.dto.*;
import com.example.pc120251backendseccion1.model.EstadoCivil;
import com.example.pc120251backendseccion1.model.Persona;
import com.example.pc120251backendseccion1.repository.PersonaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaResponseDTO crearPersona(PersonaRequestDTO dto) {
        Persona persona = Persona.builder()
                .dni(dto.getDni())
                .nombres(dto.getNombres())
                .apellidos(dto.getApellidos())
                .fechaNacimiento(dto.getFechaNacimiento())
                .sexo(dto.getSexo())
                .estadoCivil(dto.getEstadoCivil())
                .correo(dto.getCorreo())
                .build();

        return toResponse(personaRepository.save(persona));
    }

    public PersonaResponseDTO obtenerPorDni(String dni) {
        Persona persona = personaRepository.findByDni(dni)
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada"));

        return toResponse(persona);
    }

    public PersonaResponseDTO actualizarNombres(String dni, PersonaUpdateNombreDTO dto) {
        Persona persona = personaRepository.findByDni(dni)
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada"));

        persona.setNombres(dto.getNombres());
        persona.setApellidos(dto.getApellidos());

        return toResponse(personaRepository.save(persona));
    }

    public PersonaResponseDTO actualizarPadres(String dni, PersonaUpdatePadresDTO dto) {
        Persona persona = personaRepository.findByDni(dni)
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada"));

        if (dto.getDniPadre() != null) {
            persona.setPadre(personaRepository.findByDni(dto.getDniPadre())
                    .orElseThrow(() -> new EntityNotFoundException("Padre no encontrado")));
        }

        if (dto.getDniMadre() != null) {
            persona.setMadre(personaRepository.findByDni(dto.getDniMadre())
                    .orElseThrow(() -> new EntityNotFoundException("Madre no encontrada")));
        }

        return toResponse(personaRepository.save(persona));
    }

    public List<PersonaResponseDTO> listarTodos(String nombre, String estadoCivilStr) {
        List<Persona> personas;

        if (nombre != null) {
            personas = personaRepository.findByNombresContainingIgnoreCase(nombre);
        } else if (estadoCivilStr != null) {
            personas = personaRepository.findByEstadoCivil(
                    EstadoCivil.valueOf(estadoCivilStr.toUpperCase()));
        } else {
            personas = personaRepository.findAll();
        }

        return personas.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public void eliminar(String dni) {
        Persona persona = personaRepository.findByDni(dni)
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada"));

        boolean tieneDescendientes = personaRepository.findAll().stream()
                .anyMatch(p -> (p.getPadre() != null && p.getPadre().getDni().equals(dni)) ||
                        (p.getMadre() != null && p.getMadre().getDni().equals(dni)));

        if (tieneDescendientes) {
            throw new IllegalStateException("No se puede eliminar: tiene descendientes registrados.");
        }

        personaRepository.delete(persona);
    }

    public PersonaResponseDTO actualizarEstadoCivil(String dni, PersonaEstadoCivilDTO dto) {
        Persona persona = personaRepository.findByDni(dni)
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada"));

        persona.setEstadoCivil(dto.getNuevoEstadoCivil());
        return toResponse(personaRepository.save(persona));
    }

    public FamiliaResponseDTO obtenerFamilia(String dni) {
        Persona persona = personaRepository.findByDni(dni)
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada"));

        Persona padre = persona.getPadre();
        Persona madre = persona.getMadre();

        return FamiliaResponseDTO.builder()
                .padre(padre != null ? toResponse(padre) : null)
                .madre(madre != null ? toResponse(madre) : null)
                .abueloPaterno(padre != null && padre.getPadre() != null ? toResponse(padre.getPadre()) : null)
                .abuelaPaterna(padre != null && padre.getMadre() != null ? toResponse(padre.getMadre()) : null)
                .abueloMaterno(madre != null && madre.getPadre() != null ? toResponse(madre.getPadre()) : null)
                .abuelaMaterna(madre != null && madre.getMadre() != null ? toResponse(madre.getMadre()) : null)
                .build();
    }

    private PersonaResponseDTO toResponse(Persona p) {
        return PersonaResponseDTO.builder()
                .dni(p.getDni())
                .nombres(p.getNombres())
                .apellidos(p.getApellidos())
                .fechaNacimiento(p.getFechaNacimiento())
                .sexo(p.getSexo())
                .estadoCivil(p.getEstadoCivil())
                .correo(p.getCorreo())
                .dniPadre(p.getPadre() != null ? p.getPadre().getDni() : null)
                .dniMadre(p.getMadre() != null ? p.getMadre().getDni() : null)
                .build();
    }
}
