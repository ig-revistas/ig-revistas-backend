package ar.edu.unq.gurpo2.revistas.service;

import ar.edu.unq.gurpo2.revistas.model.Revista;
import ar.edu.unq.gurpo2.revistas.repository.RevistaRepository;
import jakarta.persistence.EntityNotFoundException;

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

	public Revista getRevistaById(Integer id) {
		return this.revistaRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("La revista no fue encontrada."));
	}

}
