package ar.edu.unq.gurpo2.revistas.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import ar.edu.unq.gurpo2.revistas.model.Reserva;

public class ReservaDto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    
    @JsonProperty("usuario")
    private String idUsuario;

    @JsonProperty("revista")
    private String idRevista;

    private Integer tiempoVigente;
    private LocalDate fechaPedirReserva;
    private LocalDate fechaAprobacion;
    private LocalDate fechaRechazo;
    private String estado;
    
    private String mailUsuario;
    private String tituloRevista;
    private String estadoRevista;


    public ReservaDto() {}

   
    public ReservaDto(Reserva reserva) {
        this.idUsuario = reserva.getUsuario().getId();
        this.idRevista = reserva.getRevista().getId();
        this.tiempoVigente = reserva.getTiempoVigente();
        this.fechaAprobacion = reserva.getFechaAprobacion();
        this.fechaPedirReserva = reserva.getFechaPedirReserva();
        this.fechaRechazo = reserva.getFechaRechazo();
        this.estado = reserva.getEstado();
        this.id = reserva.getId(); 
        this.mailUsuario = reserva.getUsuario().getEmail();
        this.tituloRevista = reserva.getRevista().getTitulo();
        this.estadoRevista = reserva.getRevista().getEstado();
    }

    
    public String getMailUsuario() {
		return mailUsuario;
	}


	public void setMailUsuario(String mailUsuario) {
		this.mailUsuario = mailUsuario;
	}


	public String getTituloRevista() {
		return tituloRevista;
	}


	public void setTituloRevista(String tituloRevista) {
		this.tituloRevista = tituloRevista;
	}


	public String getEstadoRevista() {
		return estadoRevista;
	}


	public void setEstadoRevista(String estadoRevista) {
		this.estadoRevista = estadoRevista;
	}


	public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getIdRevista() {
		return idRevista;
	}


	public void setIdRevista(String idRevista) {
		this.idRevista = idRevista;
	}


	public void setEstado(String estado) {
        this.estado = estado;
    }
}
