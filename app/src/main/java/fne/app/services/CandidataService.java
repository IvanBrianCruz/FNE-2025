package fne.app.services;


import fne.app.models.Candidata;
import fne.app.repository.CandidataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidataService {

    @Autowired
    private CandidataRepository candidataRepository;

    // Método para obtener todas las candidatas
    public List<Candidata> obtenerTodas() {
        return candidataRepository.findAll();
    }

    // Método para obtener una candidata por su ID
    public Optional<Candidata> obtenerPorId(Integer id) {
        return candidataRepository.findById(id);
    }

    // Método para actualizar una candidata
    public Candidata actualizar(Candidata candidata) {
        return candidataRepository.save(candidata);
    }

    // Método para "eliminar" una candidata (cambiar su estado a false)
    public void eliminar(Integer id) {
        Optional<Candidata> candidataOptional = candidataRepository.findById(id);
        if (candidataOptional.isPresent()) {
            Candidata candidata = candidataOptional.get();
            candidata.setEstado(false);
            candidataRepository.save(candidata);
        }
    }
    
    // Método para guardar una candidata
    public Candidata guardar(Candidata candidata) {
        return candidataRepository.save(candidata);
    }
}
