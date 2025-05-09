package com.example.pc120251backendseccion1.service;

import com.example.pc120251backendseccion1.model.EstadoCivil;
import com.example.pc120251backendseccion1.model.Matrimonio;
import com.example.pc120251backendseccion1.model.Persona;
import com.example.pc120251backendseccion1.repository.MatrimonioRepository;
import com.example.pc120251backendseccion1.repository.PersonaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MatrimonioService {

    private final PersonaRepository personaRepository;
    private final MatrimonioRepository matrimonioRepository;

    public void registrarMatrimonio(String dni1, String dni2) {
        String resultado = validarImpedimentos(dni1, dni2);
        if (!resultado.equals("✅ Matrimonio permitido")) {
            throw new IllegalStateException(resultado);
        }

        Persona p1 = personaRepository.findByDni(dni1)
                .orElseThrow(() -> new EntityNotFoundException("Persona 1 no encontrada"));
        Persona p2 = personaRepository.findByDni(dni2)
                .orElseThrow(() -> new EntityNotFoundException("Persona 2 no encontrada"));

        Matrimonio matrimonio = Matrimonio.builder()
                .persona1(p1)
                .persona2(p2)
                .fechaRegistro(LocalDate.now())
                .build();

        matrimonioRepository.save(matrimonio);

        p1.setEstadoCivil(EstadoCivil.CASADO);
        p2.setEstadoCivil(EstadoCivil.CASADO);
        personaRepository.save(p1);
        personaRepository.save(p2);
    }

    public String validarImpedimentos(String dni1, String dni2) {
        if (dni1.equals(dni2)) return "No puedes casarte contigo mismo.";

        Persona p1 = personaRepository.findByDni(dni1)
                .orElseThrow(() -> new EntityNotFoundException("Persona 1 no encontrada"));
        Persona p2 = personaRepository.findByDni(dni2)
                .orElseThrow(() -> new EntityNotFoundException("Persona 2 no encontrada"));

        // Edad mínima: 18 años
        if (p1.getFechaNacimiento().plusYears(18).isAfter(LocalDate.now()) ||
                p2.getFechaNacimiento().plusYears(18).isAfter(LocalDate.now())) {
            return "Alguno de los contrayentes es menor de edad.";
        }

        // Ya casado
        if (p1.getEstadoCivil() == EstadoCivil.CASADO || p2.getEstadoCivil() == EstadoCivil.CASADO) {
            return "Uno de los contrayentes ya está casado.";
        }

        // Línea recta: padre-hijo, abuelo-nieto, etc.
        if (esAscendiente(p1, p2) || esAscendiente(p2, p1)) {
            return "Matrimonio prohibido por consanguinidad en línea recta.";
        }

        // Hermanos
        if ((p1.getPadre() != null && p1.getPadre().equals(p2.getPadre())) ||
                (p1.getMadre() != null && p1.getMadre().equals(p2.getMadre()))) {
            return "Matrimonio prohibido entre hermanos.";
        }

        // Tíos y sobrinos
        if (esTio(p1, p2) || esTio(p2, p1)) {
            return "Matrimonio prohibido entre tíos y sobrinos.";
        }

        return "✅ Matrimonio permitido";
    }

    private boolean esAscendiente(Persona posibleAsc, Persona persona) {
        return persona.getPadre() != null && (
                persona.getPadre().equals(posibleAsc) ||
                        esAscendiente(posibleAsc, persona.getPadre()))
                || persona.getMadre() != null && (
                persona.getMadre().equals(posibleAsc) ||
                        esAscendiente(posibleAsc, persona.getMadre()));
    }

    private boolean esTio(Persona posibleTio, Persona persona) {
        // Verifica si el posible tío es hermano del padre o de la madre
        Persona padre = persona.getPadre();
        Persona madre = persona.getMadre();

        if (padre != null) {
            if ((padre.getPadre() != null && (padre.getPadre().equals(posibleTio.getPadre()) || padre.getPadre().equals(posibleTio.getMadre()))) ||
                    (padre.getMadre() != null && (padre.getMadre().equals(posibleTio.getPadre()) || padre.getMadre().equals(posibleTio.getMadre())))) {
                return true;
            }
        }

        if (madre != null) {
            if ((madre.getPadre() != null && (madre.getPadre().equals(posibleTio.getPadre()) || madre.getPadre().equals(posibleTio.getMadre()))) ||
                    (madre.getMadre() != null && (madre.getMadre().equals(posibleTio.getPadre()) || madre.getMadre().equals(posibleTio.getMadre())))) {
                return true;
            }
        }

        return false;
    }
}
