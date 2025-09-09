package fne.app.repository;

import fne.app.models.Candidata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidataRepository extends JpaRepository<Candidata, Integer> {
    // Spring Data JPA ya proporciona métodos comunes como save, findById, findAll, delete, etc.
    // Aquí puedes agregar métodos personalizados si los necesitas.
}
