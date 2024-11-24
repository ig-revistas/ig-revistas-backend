package ar.edu.unq.gurpo2.revistas.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import ar.edu.unq.gurpo2.revistas.model.Reserva;


public class ReservaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonProperty("usuario")
	public String idUsuario;

	@JsonProperty("revista")
	public String idRevista;
	public Integer tiempoVigente;
	
	public String id;
	public LocalDate fechaPedirReserva;
	public LocalDate fechaAprobacion;
	public LocalDate fechaRechazo;
	
	public String estado;
	public ReservaDto() {
		// Constructor sin parametros
	}

	public ReservaDto(Reserva reserva) {
		this.id = reserva.getId();
		this.idRevista = reserva.getRevista().getId();
		this.idUsuario = reserva.getUsuario().getId();
		this.tiempoVigente = reserva.getTiempoVigente();
		this.fechaAprobacion = reserva.getFechaAprobacion();
		this.fechaPedirReserva = reserva.getFechaPedirReserva();
		this.fechaRechazo = reserva.getFechaRechazo();
		this.estado=reserva.getEstado();

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

	public static long getSerialversionuid() {
		return serialVersionUID;
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

}
