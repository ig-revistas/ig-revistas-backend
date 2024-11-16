package ar.edu.unq.gurpo2.revistas.controller;

import ar.edu.unq.gurpo2.revistas.dto.RevistaDto;
import ar.edu.unq.gurpo2.revistas.model.Revista;
import ar.edu.unq.gurpo2.revistas.service.RevistaService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/revistas")
public class RevistaController {

	@Autowired
	private RevistaService revistaService;

	private static final String UPLOAD_DIR = "./uploads/";

    @PostMapping(value = "", consumes = "multipart/form-data")
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public ResponseEntity<?> crearRevista(
            @Validated @RequestPart("revista") RevistaDto nuevaRevistaDto,
            @RequestPart("portada") MultipartFile portada) {
        try {
            String originalFilename = portada.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + originalFilename);
            Files.createDirectories(path.getParent());
            Files.write(path, portada.getBytes());

			Revista nuevaRevista = nuevaRevistaDto.toEntity();
			nuevaRevista.setPortadaUrl("/uploads/" + originalFilename);

			Revista revistaCreada = revistaService.crearRevista(nuevaRevista);
			return ResponseEntity.status(HttpStatus.CREATED).body(revistaCreada);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al subir el archivo: " + e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al crear la revista: " + e.getMessage());
		}
	}

	@GetMapping()
	public ResponseEntity<List<Revista>> getRevistas() {
		String currentDir = System.getProperty("user.dir");
		System.out.println("Directorio actual de ejecución: " + currentDir);
		try {
			List<Revista> revistas = revistaService.obtenerTodasLasRevistas();
			return ResponseEntity.ok(revistas);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        try {
            Path path = Paths.get(UPLOAD_DIR + filename);
            if (!Files.exists(path)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            byte[] data = Files.readAllBytes(path);
            return ResponseEntity.ok()
                    .header("Content-Type", "image/jpeg")
                    .body(data);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
  
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    @PreAuthorize("hasAuthority('ADMIN_ROLE')")
    public ResponseEntity<?> actualizarRevista(
            @PathVariable Integer id,
            @Validated @RequestPart("revista") RevistaDto revistaActualizadaDto,
            @RequestPart(value = "portada", required = false) MultipartFile portada) {
        try {
            Optional<Revista> revistaExistenteOpt = revistaService.obtenerRevistaPorId(id);


            if (revistaExistenteOpt.isPresent()) {
                Revista revistaExistente = revistaExistenteOpt.get();

                revistaExistente.setTitulo(revistaActualizadaDto.getTitulo());
                revistaExistente.setAutor(revistaActualizadaDto.getAutor());
                revistaExistente.setFechaPublicacion(revistaActualizadaDto.getFechaPublicacion());
                revistaExistente.setCategoria(revistaActualizadaDto.getCategoria());
                revistaExistente.setEditorial(revistaActualizadaDto.getEditorial());
                revistaExistente.setEstado(revistaActualizadaDto.getEstado());
                revistaExistente.setCantidadDisponible(revistaActualizadaDto.getCantidadDisponible());
                revistaExistente.setDescripcion(revistaActualizadaDto.getDescripcion());

                if (portada != null && !portada.isEmpty()) {
                    String originalFilename = portada.getOriginalFilename();
                    Path path = Paths.get(UPLOAD_DIR + originalFilename);
                    Files.createDirectories(path.getParent());
                    Files.write(path, portada.getBytes());
                    revistaExistente.setPortadaUrl("/uploads/" + originalFilename);
                }
                revistaService.guardarRevista(revistaExistente);
                return ResponseEntity.ok("Revista actualizada con éxito");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la revista con ID " + id);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir el archivo: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar la revista: " + e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarRevista(@PathVariable Integer id) {
        Optional<Revista> revistaExistenteOpt = revistaService.obtenerRevistaPorId(id);

        if (revistaExistenteOpt.isPresent()) {
            String portadaUrl = revistaExistenteOpt.get().getPortadaUrl();
            
            if (portadaUrl != null && !portadaUrl.isEmpty()) {
                Path portadaPath = Paths.get(UPLOAD_DIR, portadaUrl); 
                if (Files.exists(portadaPath)) {
                    try {
                        Files.delete(portadaPath);
                    } catch (IOException e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error al eliminar la portada de la revista: " + e.getMessage());
                    }
                }
            }

            revistaService.eliminarRevistaPorId(id);
            return ResponseEntity.ok("Revista eliminada con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Revista no encontrada");
        }
    }

}
