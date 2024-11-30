package ar.edu.unq.gurpo2.revistas.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unq.gurpo2.revistas.dto.ReservaDto;
import ar.edu.unq.gurpo2.revistas.model.Reserva;
import ar.edu.unq.gurpo2.revistas.model.Revista;
import ar.edu.unq.gurpo2.revistas.model.Usuario;
import ar.edu.unq.gurpo2.revistas.service.ReservaService;
import ar.edu.unq.gurpo2.revistas.service.RevistaService;
import ar.edu.unq.gurpo2.revistas.service.UsuarioService;


import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RevistaService revistaService;

 
    @PostMapping(value = "/reservar", consumes = "application/json")
    @PreAuthorize("hasAuthority('USER_ROLE')")
    public ResponseEntity<String> pedirReserva(@RequestBody ReservaDto reservaDto) {

        if (reservaDto.getIdRevista() == null || reservaDto.getIdUsuario() == null) {
            return ResponseEntity.badRequest().body("Faltan datos obligatorios");
        }
        
        Optional<Usuario> opcionalUsuario = usuarioService.getUsuarioConReservasById(reservaDto.getIdUsuario());
        Optional<Revista> opcionalRevista = revistaService.getRevistaById(reservaDto.getIdRevista());
        
        if(!opcionalRevista.isPresent()) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Revista no encotrada.");
        }
        Revista revista= opcionalRevista.get();
        
        if(!opcionalUsuario.isPresent()) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encotrada.");
        }
        Usuario usuario=opcionalUsuario.get();
        
        if (revista.getCantidadDisponible() == 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No hay revistas disponibles.");
        }
        if (!revista.getEstado().equals("DISPONIBLE")) {
        	return ResponseEntity.status(HttpStatus.CONFLICT).body("La revista no esta disponible para reservar.");
        }
        if (!usuario.puedeReservar()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario " + usuario.getNombre() + " ya tiene 3 reservas actualmente.");
        }

        if (reservaService.existeReserva(reservaDto.getIdUsuario(), reservaDto.getIdRevista())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ya existe una reserva para esta revista y usuario.");
        }

        Reserva newReserva = new Reserva();
        newReserva.setRevista(revista);
        newReserva.setUsuario(usuario);
        newReserva.setFechaPedirReserva(LocalDate.now());
        newReserva.setEstado("PENDIENTE");
        
        String responseMessage = this.reservaService.addReserva(newReserva);

        revista.tomarUnaRevista();
        	
        this.usuarioService.getAllUsuariosOperador().forEach(op -> op.notificarPendiente(newReserva));

        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    @PreAuthorize("hasAuthority('OPERADOR_ROLE')")
    @PutMapping("/aprobar/{idReserva}/{tiempoVigente}")
    public ResponseEntity<String> aprobarReserva(@PathVariable String idReserva, @PathVariable Integer tiempoVigente) {

        Optional<Reserva> optionalReserva = this.reservaService.getReservaById(idReserva);

        if (optionalReserva.isPresent()) {
            Reserva reserva = optionalReserva.get();
            String responseMessage = this.reservaService.aprobarReserva(reserva, tiempoVigente);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva no encontrada.");
        }
    }

    @PreAuthorize("hasAuthority('OPERADOR_ROLE')")
    @PutMapping("/rechazar/{idReserva}")
    public ResponseEntity<String> rechazarReserva(@PathVariable String idReserva) {

        Optional<Reserva> optionalReserva = this.reservaService.getReservaById(idReserva);

        if (optionalReserva.isPresent()) {
            Reserva reserva = optionalReserva.get();
            String responseMessage = this.reservaService.rechazarReserva(reserva);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva no encontrada.");
        }
    }

    @PreAuthorize("hasAuthority('OPERADOR_ROLE')")
    @GetMapping("/todas")
    public List<ReservaDto> todasLasReservas() {
        List<Reserva> reservas = this.reservaService.getAllReserva();
        return reservas.stream().map(reserva -> new ReservaDto(reserva)).collect(Collectors.toList());
    }

    
    @PreAuthorize("hasAuthority('OPERADOR_ROLE')")
    @GetMapping("/todasLasPendientes")
    public List<ReservaDto> todasLasReservasPendientes() {
        List<Reserva> reservas = this.reservaService.getAllReservaPendiente();
        return reservas.stream().map(reserva -> new ReservaDto(reserva)).collect(Collectors.toList());
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ReservaDto>> obtenerReservas(@PathVariable String usuarioId) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(usuarioId);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<Reserva> reservas = usuarioService.obtenerReservasDeUsuario(usuarioId);
        List<ReservaDto> reservasDto = reservas.stream()
            .map(ReservaDto::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(reservasDto);
    }


}
