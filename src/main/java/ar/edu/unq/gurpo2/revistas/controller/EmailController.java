package ar.edu.unq.grupo2.revistas.controller;

import ar.edu.unq.grupo2.revistas.service.CorreoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @Autowired
    private CorreoService correoService;

    @GetMapping("/send-email")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String text) {
        try {
            correoService.sendEmail(to, subject, text);
            return "Correo enviado con éxito a: " + to;
        } catch (Exception e) {
            return "Error al enviar el correo: " + e.getMessage();
        }
    }
}
