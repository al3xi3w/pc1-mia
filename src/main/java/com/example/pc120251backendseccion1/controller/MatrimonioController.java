package com.example.pc120251backendseccion1.controller;

import com.example.pc120251backendseccion1.service.MatrimonioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matrimonios")
@RequiredArgsConstructor
public class MatrimonioController {

    private final MatrimonioService matrimonioService;

    // Endpoint 7 - Registrar matrimonio
    @PostMapping
    public ResponseEntity<String> registrarMatrimonio(
            @RequestParam String dni1,
            @RequestParam String dni2) {
        matrimonioService.registrarMatrimonio(dni1, dni2);
        return ResponseEntity.ok("Matrimonio registrado correctamente");
    }

    // Endpoint 8 - Validar impedimentos legales
    @GetMapping("/validar/{dni1}/{dni2}")
    public ResponseEntity<String> validarMatrimonio(
            @PathVariable String dni1,
            @PathVariable String dni2) {
        String resultado = matrimonioService.validarImpedimentos(dni1, dni2);
        return ResponseEntity.ok(resultado);
    }
}
