package ar.edu.unq.gurpo2.revistas.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import ar.edu.unq.gurpo2.revistas.model.Reserva;

public class ReservaDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonProperty("usuario")
    private String idUsuario;

    @JsonProperty("revista")
    private RevistaDto revista;

    private Integer tiempoVigente;
    private LocalDate fechaPedirReserva;
    private LocalDate fechaAprobacion;
    private LocalDate fechaRechazo;
    private String estado;

    private String idRevista;


    public ReservaDto() {}

   
    public ReservaDto(Reserva reserva) {
        this.idUsuario = reserva.getUsuario().getId();
        this.revista = new RevistaDto(reserva.getRevista());
        this.tiempoVigente = reserva.getTiempoVigente();
        this.fechaAprobacion = reserva.getFechaAprobacion();
        this.fechaPedirReserva = reserva.getFechaPedirReserva();
        this.fechaRechazo = reserva.getFechaRechazo();
        this.estado = reserva.getEstado();
        this.idRevista = reserva.getRevista().getId(); 
    }


    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdRevista() {
        return idRevista;
    }

    public void setIdRevista(String idRevista) {
        this.idRevista = idRevista;
    }

    public Integer getTiempoVigente() {
        return tiempoVigente;
    }

    public void setTiempoVigente(Integer tiempoVigente) {
        this.tiempoVigente = tiempoVigente;
    }

    public LocalDate getFechaPedirReserva() {
        return fechaPedirReserva;
    }

    public void setFechaPedirReserva(LocalDate fechaPedirReserva) {
        this.fechaPedirReserva = fechaPedirReserva;
    }

    public LocalDate getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(LocalDate fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public LocalDate getFechaRechazo() {
        return fechaRechazo;
    }

    public void setFechaRechazo(LocalDate fechaRechazo) {
        this.fechaRechazo = fechaRechazo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
