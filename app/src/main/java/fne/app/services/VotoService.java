package fne.app.services;


import fne.app.models.Voto;
import fne.app.models.Candidata;
import fne.app.repository.VotoRepository;
import fne.app.repository.CandidataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VotoService {

    @Autowired
    private VotoRepository votoRepository;
    
    @Autowired
    private CandidataRepository candidataRepository;

    // Método para guardar un nuevo voto
    @Transactional
    public Voto guardar(Voto voto) throws IllegalStateException {
        // Verificar si el jurado ya votó por esta candidata
        boolean votoExistente = votoRepository.existsByJuradoIdAndCandidataId(
            voto.getJurado().getId(), voto.getCandidata().getId());

        if (votoExistente) {
            throw new IllegalStateException("Ya has votado por esta candidata.");
        }
        
        // Actualizar el puntaje total de la candidata
        Candidata candidata = voto.getCandidata();
        if (candidata != null) {
            candidata.setPuntajeTotal(candidata.getPuntajeTotal() + voto.getPuntaje());
            candidataRepository.save(candidata);
        }

        return votoRepository.save(voto);
    }
    
    // Método para obtener todos los votos
    public List<Voto> obtenerTodos() {
        return votoRepository.findAll();
    }
}
