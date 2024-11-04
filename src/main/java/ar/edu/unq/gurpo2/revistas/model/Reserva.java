package ar.edu.unq.gurpo2.revistas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Reserva", schema = "revistas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    private Integer tiempoVigente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_revista", referencedColumnName = "id")
    private Revista revista;

    public Reserva() {}

    public Reserva(Integer tiempoVigente, Usuario usuario, Revista revista) {
        this.tiempoVigente = tiempoVigente;
        this.usuario = usuario;
        this.revista = revista;
    }

    public Long getId() { 
        return id;
    }

    public void setId(Long id) {
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
}
