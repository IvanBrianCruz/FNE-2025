package fne.app.services;

import fne.app.models.Candidata;
import fne.app.models.Voto;
import fne.app.repository.CandidataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CandidataService {

    @Autowired
    private CandidataRepository candidataRepository;

    // --- MÉTODOS EXISTENTES (sin cambios) ---

    public List<Candidata> obtenerTodas() {
        return candidataRepository.findAll();
    }

    public Optional<Candidata> obtenerPorId(Integer id) {
        return candidataRepository.findById(id);
    }
    
    public Candidata guardar(Candidata candidata) {
        return candidataRepository.save(candidata);
    }

    public Candidata actualizar(Candidata candidata) {
        return candidataRepository.save(candidata);
    }
    
    public void eliminar(Integer id) {
        Optional<Candidata> candidataOptional = candidataRepository.findById(id);
        if (candidataOptional.isPresent()) {
            Candidata candidata = candidataOptional.get();
            candidata.setEstado(false);
            candidataRepository.save(candidata);
        }
    }
    
    // --- NUEVOS MÉTODOS PARA LA LÓGICA DE NEGOCIO ---

    /**
     * Calcula el puntaje total para todas las candidatas activas.
     * Este método debe ser invocado cuando la votación ha finalizado.
     * Itera sobre cada candidata, suma los subtotales de todos sus votos
     * y actualiza su campo puntajeTotal.
     */
    @Transactional
    public void calcularPuntajesTotales() {
        List<Candidata> candidatas = candidataRepository.findByEstadoTrue(); // Asumiendo que tienes este método en el repo
        
        for (Candidata candidata : candidatas) {
            // Usamos un stream para sumar los subtotales de todos los votos de la candidata
            Double puntajeFinal = candidata.getVotos().stream()
                .mapToDouble(Voto::getSubtotal)
                .sum();
            
            candidata.setPuntajeTotal(puntajeFinal);
            candidataRepository.save(candidata);
        }
    }

    /**
     * Genera un reporte completo del escrutinio.
     * Devuelve una lista de todas las candidatas con todos sus datos,
     * incluyendo una lista detallada de cada voto recibido (juez, puntajes, subtotal).
     * Ideal para ser consumido por un endpoint que genere un PDF o una vista de reporte.
     * @return Lista de candidatas con toda la información de la votación.
     */
    public List<Candidata> generarEscrutinioCompleto() {
        // Simplemente devolvemos todas las candidatas.
        // Gracias a las relaciones de JPA, al obtener una candidata, también podemos acceder a su lista de votos
        // y a través de cada voto, al jurado que lo emitió.
        // Es importante que la transacción que llame a este método no se cierre para evitar LazyInitializationException.
        // O configurar el FetchType de la relación a EAGER.
        return candidataRepository.findAll();
    }
}
