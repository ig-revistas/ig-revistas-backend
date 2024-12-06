package ar.edu.unq.gurpo2.revistas.controller;

import ar.edu.unq.gurpo2.revistas.service.CorreoService;
import ar.edu.unq.gurpo2.revistas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private CorreoService correoService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/olvide-contrasenia")
    public String enviarCorreoRecuperacion(@RequestParam String email) {
        try {
            UUID token = UUID.randomUUID();
            usuarioService.guardarTokenRecuperacion(email, token.toString());

            String subject = "Restablecer contraseña";
            String resetUrl = "http://localhost:3000/restablecer-contrasenia?token=" + token;
            String text = "<p>Haz clic en el siguiente enlace para restablecer tu contraseña:</p>" +
                          "<a href='" + resetUrl + "'>Restablecer Contraseña</a>";

            correoService.sendEmail(email, subject, text);
            return "Correo enviado para restablecer contraseña a: " + email;
        } catch (Exception e) {
            return "Error al enviar el correo: " + e.getMessage();
        }
    }
}
