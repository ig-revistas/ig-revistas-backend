package ar.edu.unq.gurpo2.revistas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import ar.edu.unq.gurpo2.revistas.model.Usuario;

import java.util.UUID;

@Service
public class CorreoService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UsuarioService usuarioService;

    public void sendEmail(String to, String subject, String text) throws MailException, jakarta.mail.MessagingException {
        jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
        helper.setFrom("revistatp8@gmail.com");
        mailSender.send(message);
    }

    public void enviarRecuperacionContrasenia(String email) throws jakarta.mail.MessagingException {
        Usuario usuario = usuarioService.buscarPorEmail(email);
        String tokenRecuperacion = UUID.randomUUID().toString();
        usuario.setTokenRecuperacion(tokenRecuperacion);
        usuarioService.actualizarUsuario(usuario);

        String subject = "Recuperación de contraseña";
        String text = String.format(
            "Hola %s,\n\nPara restablecer tu contraseña, utiliza el siguiente token:\n\n%s\n\nSi no solicitaste este cambio, ignora este mensaje.",
            usuario.getNombre(),
            tokenRecuperacion
        );

        sendEmail(email, subject, text);
    }
}
