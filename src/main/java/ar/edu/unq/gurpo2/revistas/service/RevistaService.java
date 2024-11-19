package ar.edu.unq.gurpo2.revistas.service;

import ar.edu.unq.gurpo2.revistas.model.Revista;
import ar.edu.unq.gurpo2.revistas.repository.RevistaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RevistaService {

    @Autowired
    private RevistaRepository revistaRepository;

    
    public Revista crearRevista(Revista nuevaRevista) {
        
        nuevaRevista.setId(UUID.randomUUID().toString()); 
        return revistaRepository.save(nuevaRevista);
    }

   
    public List<Revista> obtenerTodasLasRevistas() {
        return revistaRepository.findAll();
    }

   
    public Optional<Revista> obtenerRevistaPorId(String id) {
        return revistaRepository.findById(id);
    }

    
    public Revista guardarRevista(Revista revista) {
        return revistaRepository.save(revista);
    }

   
    public void eliminarRevistaPorId(String id) {
        revistaRepository.deleteById(id);
    }

    
    public Revista getRevistaById(String id) {
        return this.revistaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La revista no fue encontrada."));
    }
}
