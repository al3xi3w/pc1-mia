package com.example.pc120251backendseccion1.controller;

import com.example.pc120251backendseccion1.dto.*;
import com.example.pc120251backendseccion1.model.Usuario;
import com.example.pc120251backendseccion1.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Endpoint 11 - Registrar nuevo usuario (solo ADMIN)
    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> registrar(@RequestBody RegistroUsuarioDTO dto) {
        authService.registrar(dto);
        return ResponseEntity.ok().build();
    }

    // Endpoint 12 - Login público
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    // Endpoint 13 - Obtener datos del usuario autenticado
    @GetMapping("/me")
    public ResponseEntity<Usuario> me() {
        return ResponseEntity.ok(authService.me());
    }

    // Endpoint 14 - Cambiar contraseña
    @PutMapping("/change-password")
    public ResponseEntity<Void> cambiarPassword(@RequestBody String nuevaClave) {
        authService.cambiarPassword(nuevaClave);
        return ResponseEntity.ok().build();
    }
}
