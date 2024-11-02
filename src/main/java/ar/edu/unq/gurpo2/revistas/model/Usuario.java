package ar.edu.unq.gurpo2.revistas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import java.util.List;

@Entity
@Table(name = "Usuario", schema = "revistas")
public class Usuario {
    @Id
    private String id;
    private String nombre;
    private String email;
    private String contrasenia;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "Usuario_Rol", 
        joinColumns = @JoinColumn(name = "id_usuario"), 
        inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private List<Rol> roles; 

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
}
