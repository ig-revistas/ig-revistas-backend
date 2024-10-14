package Revistas.Trabajo.controller;

import Revistas.Trabajo.DTO.RevistaDto;
import Revistas.Trabajo.model.Revista;
import Revistas.Trabajo.service.RevistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/revistas")
public class RevistaController {

    @Autowired
    private RevistaService revistaService;

    @PostMapping()
    // @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public ResponseEntity<?> crearRevista(@Validated @RequestBody RevistaDto nuevaRevistaDto) {
        try {
            Revista nuevaRevista = nuevaRevistaDto.toEntity(); 
            Revista revistaCreada = revistaService.crearRevista(nuevaRevista);
            return ResponseEntity.status(HttpStatus.CREATED).body(revistaCreada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la revista: " + e.getMessage());
        }
    }
}

