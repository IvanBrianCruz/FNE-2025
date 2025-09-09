package fne.app.security;

import fne.app.models.Jurado;
import fne.app.repository.JuradoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Esta clase se ejecuta al iniciar la aplicación para inicializar datos en la base de datos.
 * En este caso, crea un usuario jurado por defecto si no existe.
 */
@Component
public class InitialDataSeeder implements CommandLineRunner {

    private final JuradoRepository juradoRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitialDataSeeder(JuradoRepository juradoRepository, PasswordEncoder passwordEncoder) {
        this.juradoRepository = juradoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Verifica si ya existen jurados en la base de datos
        if (juradoRepository.count() == 0) {
            System.out.println("Creando 5 cuentas de Jurado iniciales...");

            // Define las contraseñas personalizadas aquí
            String[] passwords = {"72346", "95173", "17359", "17561", "17528"};

            for (int i = 1; i <= 5; i++) {
                Jurado jurado = new Jurado();
                jurado.setNombre("Juez " + i);
                jurado.setUsuario("jurado" + i);
                
                // Asigna la contraseña personalizada y la encripta
                jurado.setPassword(passwordEncoder.encode(passwords[i-1])); 
                jurado.setEstado(true);
                juradoRepository.save(jurado);
                
                System.out.println("Usuario 'jurado" + i + "' creado con éxito.");
            }
        } else {
            System.out.println("La base de datos ya contiene jurados. No se crearán nuevos.");
        }
    }
}
