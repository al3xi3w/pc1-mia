package com.example.pc120251backendseccion1.dto;

import com.example.pc120251backendseccion1.model.Rol;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroUsuarioDTO {
    private String username;
    private String password;
    private Rol rol;
}
