package com.example.pc120251backendseccion1.controller;

import com.example.pc120251backendseccion1.dto.*;
import com.example.pc120251backendseccion1.service.PersonaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/personas")
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaService personaService;

    // Endpoint 1 - Crear persona
    @PostMapping
    public ResponseEntity<PersonaResponseDTO> crearPersona(@Valid @RequestBody PersonaRequestDTO dto) {
        return ResponseEntity.ok(personaService.crearPersona(dto));
    }

    // Endpoint 2 - Obtener persona por DNI
    @GetMapping("/{dni}")
    public ResponseEntity<PersonaResponseDTO> obtenerPorDni(@PathVariable String dni) {
        return ResponseEntity.ok(personaService.obtenerPorDni(dni));
    }

    // Endpoint 3 - Actualizar nombres y apellidos
    @PutMapping("/{dni}/nombres")
    public ResponseEntity<PersonaResponseDTO> actualizarNombres(
            @PathVariable String dni,
            @Valid @RequestBody PersonaUpdateNombreDTO dto) {
        return ResponseEntity.ok(personaService.actualizarNombres(dni, dto));
    }

    // Endpoint 4 - Actualizar padres
    @PutMapping("/{dni}/padres")
    public ResponseEntity<PersonaResponseDTO> actualizarPadres(
            @PathVariable String dni,
            @RequestBody PersonaUpdatePadresDTO dto) {
        return ResponseEntity.ok(personaService.actualizarPadres(dni, dto));
    }

    // Endpoint 5 - Eliminar persona (solo si no tiene descendientes)
    @DeleteMapping("/{dni}")
    public ResponseEntity<Void> eliminar(@PathVariable String dni) {
        personaService.eliminar(dni);
        return ResponseEntity.noContent().build();
    }

    // Endpoint 6 - Listar personas (con filtros opcionales)
    @GetMapping
    public ResponseEntity<List<PersonaResponseDTO>> listarTodos(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false, name = "estado-civil") String estadoCivil) {
        return ResponseEntity.ok(personaService.listarTodos(nombre, estadoCivil));
    }
}
