package fne.app.services;


import fne.app.models.Jurado;
import fne.app.repository.JuradoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JuradoService {

    @Autowired
    private JuradoRepository juradoRepository;

    // Método para guardar un nuevo jurado
    public Jurado guardar(Jurado jurado) {
        // En la vida real, aquí encriptarías la contraseña antes de guardar.
        // Por ahora, solo guardamos el objeto.
        return juradoRepository.save(jurado);
    }

    // Método para encontrar un jurado por su ID
    public Optional<Jurado> encontrarPorId(Integer id) {
        return juradoRepository.findById(id);
    }

    // Método para encontrar un jurado por su nombre de usuario (utilizado en la autenticación)
    public Jurado encontrarPorUsuario(String usuario) {
        return juradoRepository.findByUsuario(usuario);
    }
}
