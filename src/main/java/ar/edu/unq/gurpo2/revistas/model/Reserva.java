package ar.edu.unq.gurpo2.revistas.model;

import java.time.LocalDate;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;

@Entity
@NamedEntityGraph(name = "ReservaWithUsuario", 
    attributeNodes = @NamedAttributeNode("usuario"))
@NamedEntityGraph(name = "ReservaWithRevista", 
    attributeNodes = @NamedAttributeNode("revista"))
@NamedEntityGraph(name = "ReservaWithRevistaAndUsuario", 
    attributeNodes = {
        @NamedAttributeNode("usuario"),
        @NamedAttributeNode("revista")
    })
@Table(name = "Reserva", schema = "revistas")
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	private Integer tiempoVigente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario", referencedColumnName = "id")
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_revista", referencedColumnName = "id")
	private Revista revista;

	private LocalDate fechaPedirReserva;
	private LocalDate fechaAprobacion;
	private LocalDate fechaRechazo;
	
	private String estado;
	public Reserva() {
	}

	public Reserva(Integer tiempoVigente, Usuario usuario, Revista revista ,String estado) {
		this.tiempoVigente = tiempoVigente;
		this.usuario = usuario;
		this.revista = revista;
		this.fechaAprobacion = null;
		this.fechaPedirReserva = null;
		this.fechaRechazo = null;
		this.estado = estado;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getTiempoVigente() {
		return tiempoVigente;
	}

	public void setTiempoVigente(Integer tiempoVigente) {
		this.tiempoVigente = tiempoVigente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Revista getRevista() {
		return revista;
	}

	public void setRevista(Revista revista) {
		this.revista = revista;
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
