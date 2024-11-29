package ar.edu.unq.gurpo2.revistas.service;

import ar.edu.unq.gurpo2.revistas.model.Estado;
import ar.edu.unq.gurpo2.revistas.model.Revista;
import ar.edu.unq.gurpo2.revistas.repository.RevistaRepository;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RevistaService {

    @Autowired
    private RevistaRepository revistaRepository;

    public Revista crearRevista(Revista nuevaRevista) {
        return revistaRepository.save(nuevaRevista);
    }

	public List<Revista> obtenerTodasLasRevistas() {
		return revistaRepository.findAll(); 
    }
	public Optional<Revista> obtenerRevistaPorId(Integer id) {
        return revistaRepository.findById(id);
    }

    public Revista guardarRevista(Revista revista) {
        return revistaRepository.save(revista);
    }
	
    public void eliminarRevistaPorId(Integer id) {
        revistaRepository.deleteById(id);
    }
    
    public Revista suspenderRevista(int id, int diasSuspension) {
        if (diasSuspension <= 0) {
            throw new IllegalArgumentException("Los días de suspensión deben ser mayores a 0.");
        }

        Revista revista = revistaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Revista no encontrada con el ID: " + id));

        revista.setEstado(Estado.SUSPENDIDA); 

        return revistaRepository.save(revista);
    }
    
}

