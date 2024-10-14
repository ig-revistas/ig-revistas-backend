package Revistas.Trabajo.repository;

import Revistas.Trabajo.model.Revista;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevistaRepository extends JpaRepository<Revista, Integer> {
    
}