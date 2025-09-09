package fne.app.security;


import fne.app.models.Jurado;
import fne.app.repository.JuradoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private JuradoRepository juradoRepository;

    @Override
    public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
        // Busca al jurado por su nombre de usuario
        Jurado jurado = juradoRepository.findByUsuario(usuario);
        if (jurado == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + usuario);
        }
        
        // Retorna un objeto UserDetails de Spring Security con la información del jurado
        // La contraseña se obtiene con jurado.getPassword()
        return new User(jurado.getUsuario(), jurado.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_JURADO")));
    }
}