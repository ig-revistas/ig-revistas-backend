package ar.edu.unq.gurpo2.revistas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import java.time.LocalDate;

@Entity
@Table(name = "Revista", schema = "revistas")
public class Revista {

    @Id
    private String id;  

    private String titulo;
    private String autor;
    private LocalDate fechaPublicacion;
    private String categoria;
    private String editorial;
    private int cantidadDisponible;
    private String descripcion;
    private String portadaUrl;
    
    @Enumerated(EnumType.STRING)
    private Estado estado;
    
    public int getId() {

    public Revista() {
        this.id = UUID.randomUUID().toString(); 
    }

   
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

 
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPortadaUrl() {
        return portadaUrl;
    }

    public void setPortadaUrl(String portadaUrl) {
        this.portadaUrl = portadaUrl;
    }


	public void tomarUnaRevista() {
		// TODO Auto-generated method stub
		
	}
}
