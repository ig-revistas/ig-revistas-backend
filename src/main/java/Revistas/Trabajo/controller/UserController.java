package Revistas.Trabajo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Revistas.Trabajo.DTO.UserAuthDto;
import Revistas.Trabajo.DTO.UsuarioDto;
import Revistas.Trabajo.model.Usuario;
import Revistas.Trabajo.request.AuthRequest;
import Revistas.Trabajo.security.UserInfoDetails;
import Revistas.Trabajo.service.JwtService;
import Revistas.Trabajo.service.UsuarioService;

@RestController
@RequestMapping()
public class UserController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

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

    @GetMapping("/perfil-admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generate-token")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            if (authentication.isAuthenticated()) {
                Usuario usuario = ((UserInfoDetails) authentication.getPrincipal()).getUsuario();
                String token = jwtService.generateToken(authRequest.getUsername());

                UsuarioDto usuarioDto = new UsuarioDto(usuario);
                usuarioDto.setPassword(null); 

                return ResponseEntity.ok(new UserAuthDto(token, usuarioDto));
            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Error de autenticación: " + e.getMessage());
        }
    }
}
