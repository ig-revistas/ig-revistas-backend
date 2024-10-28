package Revistas.Trabajo.service;

import Revistas.Trabajo.model.Revista;
import Revistas.Trabajo.repository.RevistaRepository;

import java.util.List;

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
		
}

