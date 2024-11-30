package ar.edu.unq.gurpo2.revistas.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedEntityGraph(name="UsuarioConRoles",
attributeNodes = @NamedAttributeNode("roles"))

@NamedEntityGraph(name="UsarioConReservas",
attributeNodes = @NamedAttributeNode("reservas"))

@NamedEntityGraph(name="UsuarioConRolesYReservas",
attributeNodes = { @NamedAttributeNode("roles"),
		@NamedAttributeNode("reservas")})

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
    
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Reserva> reservas = new ArrayList<>();//<- reservas aprovadas o en pendiente
	
	@Column(name = "portada_url")
    private String portadaUrl;
	
	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}
	

    public String getPortadaUrl() {
        return portadaUrl;
    }

    public void setPortadaUrl(String portadaUrl) {
        this.portadaUrl = portadaUrl;
    }
     

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

	public boolean puedeReservar() {
		long reservasActivas = reservas.stream()
                 .filter(reserva -> reserva.getEstado().equals("PENDIENTE")|| reserva.getEstado().equals("APROBADA"))
                 .count();
		return reservasActivas < 3;
	}

	public void addReserva(Reserva reserva) {
		if (this.puedeReservar()) {
			this.reservas.add(reserva);
		}
	}
	public Boolean tieneRolo(String rol) {
		return this.roles
				   .stream()
				   .map(r->r.getNombre())
				   .anyMatch(n->n.equals(rol));
	}
	public void notificarPendiente(Reserva newReserva) {
		if(this.tieneRolo("OPERADOR_ROLE")) {
			System.out.println("Notificación: Nueva reserva pendiente para revisión. Detalles de la reserva: " + newReserva);
		}
	}
//	public void eliminarRecervaRechazada(Reserva recerva) {
//		 this.reservas= this.reservas.stream()
//					 		.filter(r-> !r.equals(recerva))
//					 		.collect(Collectors.toList());
//	}
}
