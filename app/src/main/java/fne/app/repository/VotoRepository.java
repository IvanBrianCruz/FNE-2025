package fne.app.repository;

import fne.app.models.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Integer> {
    // Método para verificar si un jurado ya votó por una candidata específica
    boolean existsByJuradoIdAndCandidataId(Integer juradoId, Integer candidataId);
}
