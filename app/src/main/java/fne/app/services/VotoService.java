package fne.app.services;

import fne.app.models.Voto;
import fne.app.repository.VotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;

    /**
     * Guarda un nuevo voto con los 3 puntajes emitidos por un jurado.
     * La lógica de sumar el puntaje total de la candidata ha sido removida de aquí.
     * El subtotal del voto se calcula automáticamente gracias a la anotación @PrePersist en la entidad Voto.
     */
    @Transactional
    public Voto guardar(Voto voto) throws IllegalStateException {
        // Se mantiene la verificación para asegurar que un jurado vote solo una vez por candidata.
        boolean votoExistente = votoRepository.existsByJuradoIdAndCandidataId(
            voto.getJurado().getId(), voto.getCandidata().getId());

        if (votoExistente) {
            throw new IllegalStateException("El jurado ya ha emitido su voto para esta candidata.");
        }
        
        // Simplemente se guarda el voto. El subtotal se calculará automáticamente.
        // Ya no se modifica el puntajeTotal de la candidata en este paso.
        return votoRepository.save(voto);
    }
    
    /**
     * Método para obtener todos los votos registrados en el sistema.
     */
    public List<Voto> obtenerTodos() {
        return votoRepository.findAll();
    }
}
