package fne.app.repository;

import fne.app.models.Candidata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidataRepository extends JpaRepository<Candidata, Integer> {
    // Spring Data JPA ya proporciona métodos comunes como save, findById, findAll, delete, etc.
    
    /**
     * Nuevo método para encontrar todas las candidatas cuyo campo 'estado' es true.
     * Spring Data JPA generará automáticamente la consulta necesaria a partir del nombre del método.
     * @return una lista de candidatas activas.
     */
    List<Candidata> findByEstadoTrue();
}
