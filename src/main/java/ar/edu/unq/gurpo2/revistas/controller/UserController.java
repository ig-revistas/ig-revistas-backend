package ar.edu.unq.gurpo2.revistas.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.unq.gurpo2.revistas.dto.UserAuthDto;
import ar.edu.unq.gurpo2.revistas.dto.UsuarioDto;
import ar.edu.unq.gurpo2.revistas.model.Usuario;
import ar.edu.unq.gurpo2.revistas.request.AuthRequest;
import ar.edu.unq.gurpo2.revistas.security.UserInfoDetails;
import ar.edu.unq.gurpo2.revistas.service.JwtService;
import ar.edu.unq.gurpo2.revistas.service.UsuarioService;
import io.jsonwebtoken.io.IOException;


@RestController
@RequestMapping()
public class UserController {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final String UPLOAD_DIR = "./uploads/";

	@GetMapping("/home")
	public String welcome() {
		return "Welcome! This endpoint is not secure.";
	}

	@PostMapping("/registrarse")
	public ResponseEntity<?> registerUser(@RequestBody UsuarioDto userDto) {
		if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) {
			return ResponseEntity.badRequest().body("La contraseña no puede estar vacía ni ser null");
		}

		Usuario userInfo = new Usuario();
		userInfo.setNombre(userDto.getName());
		userInfo.setEmail(userDto.getEmail());
		userInfo.setContrasenia(userDto.getPassword());

		String responseMessage = usuarioService.addUser(userInfo);
		return ResponseEntity.ok(responseMessage);
	}

    @GetMapping("/perfil")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

	@PostMapping("/login")
	public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

			if (authentication.isAuthenticated()) {
				Usuario usuario = ((UserInfoDetails) authentication.getPrincipal()).getUsuario();
				String token = jwtService.generateToken(authRequest.getUsername());

                UsuarioDto usuarioDto = new UsuarioDto(usuario);
                usuarioDto.setPassword(null);
                usuarioDto.setPortadaUrl(usuario.getPortadaUrl());

                return ResponseEntity.ok(new UserAuthDto(token, usuarioDto));
            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Error de autenticación: " + e.getMessage());
        }
    }

    @PostMapping(value = "/subir-portada", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('USER_ROLE')")
    public ResponseEntity<?> subirPortada(@RequestPart("portada") MultipartFile portada, Authentication authentication) {
        try {
            String usuarioId = ((UserInfoDetails) authentication.getPrincipal()).getUsuario().getId();

            String originalFilename = portada.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + originalFilename);
            Files.createDirectories(path.getParent());
            Files.write(path, portada.getBytes());

            Usuario usuario = usuarioService.getUsuarioById(usuarioId);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }

            usuario.setPortadaUrl("/uploads/" + originalFilename);
            usuarioService.actualizarUsuario(usuario);

            return ResponseEntity.ok("Foto de portada subida con éxito");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la foto: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el usuario: " + e.getMessage());
        }
    }
    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<byte[]> getUserCoverImage(@PathVariable String filename) throws java.io.IOException {
        try {
            Path path = Paths.get(UPLOAD_DIR + filename);
            if (!Files.exists(path)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
             byte[] data = Files.readAllBytes(path);
            return ResponseEntity.ok()
                    .header("Content-Type", "image/jpeg")  
                    .body(data); 
        }catch (IOException e) {  
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

