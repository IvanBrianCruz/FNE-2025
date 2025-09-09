package fne.app.repository;

import fne.app.models.Jurado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JuradoRepository extends JpaRepository<Jurado, Integer> {
    // Método para buscar un Jurado por su nombre de usuario
    Jurado findByUsuario(String usuario);
}
