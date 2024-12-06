package ar.edu.unq.gurpo2.revistas.controller;

import ar.edu.unq.gurpo2.revistas.model.Usuario;
import ar.edu.unq.gurpo2.revistas.service.CorreoService;
import ar.edu.unq.gurpo2.revistas.service.JwtService;
import ar.edu.unq.gurpo2.revistas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; // Importar para inyectar la URL base
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private CorreoService correoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @Value("${app.url}") 
    private String appUrl; 

    @PostMapping("/olvide-contrasenia")
    public String enviarCorreoRecuperacion(@RequestParam String email) {
        try {
            Usuario usuario = usuarioService.buscarPorEmail(email);
            if (usuario == null) {
                return "Usuario no encontrado.";
            }

            String token = jwtService.generateToken(email); 

            usuarioService.guardarTokenRecuperacion(email, token);

            String subject = "Restablecer contraseña";
            String resetUrl = appUrl + "/restablecer-contrasenia?token=" + token; 
            String text = "<p>Haz clic en el siguiente enlace para restablecer tu contraseña:</p>" +
                          "<a href='" + resetUrl + "'>Restablecer Contraseña</a>";

            correoService.sendEmail(email, subject, text);
            return "Correo enviado para restablecer contraseña a: " + email;
        } catch (Exception e) {
            return "Error al enviar el correo: " + e.getMessage();
        }
    }
}
