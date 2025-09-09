package fne.app.controllers;

import fne.app.models.Candidata;
import fne.app.models.Jurado;
import fne.app.models.Voto;
import fne.app.services.CandidataService;
import fne.app.services.JuradoService;
import fne.app.services.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class VotoController {

    @Autowired
    private CandidataService candidataService;

    @Autowired
    private JuradoService juradoService;

    @Autowired
    private VotoService votoService;

    @GetMapping("/votar")
    public String paginaVotacion(Model model) {
        List<Candidata> candidatas = candidataService.obtenerTodas();
        model.addAttribute("candidatas", candidatas);
        return "votacion"; // Devuelve la plantilla HTML (votacion.html)
    }

    /**
     * Muestra la página de votación individual para una candidata específica.
     * El ID de la candidata seleccionada.
     *  El modelo para pasar datos a la vista.
     *  La plantilla de votación individual o redirige si la candidata no existe.
     */
    @GetMapping("/votar/{id}")
    public String paginaVotacionIndividual(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        return candidataService.obtenerPorId(id)
                .map(candidata -> {
                    model.addAttribute("candidata", candidata);
                    return "voto-individual";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Candidata no encontrada.");
                    return "redirect:/votar";
                });
    }

    /**
     * Registra el voto del jurado para una candidata seleccionada.
     *  El ID de la candidata a la que se vota.
     * El puntaje asignado.
     *  Atributos para redirigir con mensajes.
     *  Redirección a la página principal de votación.
     */
    @PostMapping("/votar/enviar")
    public String registrarVoto(@RequestParam Integer candidataId,
                                @RequestParam Double puntaje,
                                RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuarioActual = auth.getName();

        Jurado jurado = juradoService.encontrarPorUsuario(usuarioActual);
        Candidata candidata = candidataService.obtenerPorId(candidataId).orElse(null);

        if (jurado != null && candidata != null) {
            Voto voto = new Voto();
            voto.setJurado(jurado);
            voto.setCandidata(candidata);
            voto.setPuntaje(puntaje);

            try {
                votoService.guardar(voto);
                redirectAttributes.addFlashAttribute("successMessage", "¡Voto registrado con éxito!");
            } catch (IllegalStateException e) {
                redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error al registrar el voto. Inténtelo de nuevo.");
        }

        return "redirect:/votar";
    }

    @GetMapping("/resultados")
    public String mostrarResultados(Model model) {
        List<Candidata> candidatas = candidataService.obtenerTodas();
        
        // Ordenar las candidatas por puntaje total de mayor a menor
        List<Candidata> candidatasOrdenadas = candidatas.stream()
                .sorted((c1, c2) -> Double.compare(c2.getPuntajeTotal(), c1.getPuntajeTotal()))
                .collect(Collectors.toList());
                
        model.addAttribute("candidatas", candidatasOrdenadas);
        return "resultados"; // Devuelve la plantilla HTML (resultados.html)
    }

    @GetMapping("/resultados/{id}")
    public String mostrarDetallesCandidata(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        return candidataService.obtenerPorId(id)
                .map(candidata -> {
                    model.addAttribute("candidata", candidata);
                    model.addAttribute("votos", candidata.getVotos());
                    return "resultados-detalle";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Candidata no encontrada.");
                    return "redirect:/resultados";
                });
    }
}
