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

import java.util.Comparator;
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
        return "votacion";
    }

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
     * Registra el voto de un jurado para una candidata con tres puntajes.
     */
    @PostMapping("/votar/enviar")
    public String registrarVoto(@RequestParam Integer candidataId,
                                @RequestParam Double puntaje1,
                                @RequestParam Double puntaje2,
                                @RequestParam Double puntaje3,
                                RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuarioActual = auth.getName();

        Jurado jurado = juradoService.encontrarPorUsuario(usuarioActual);
        Candidata candidata = candidataService.obtenerPorId(candidataId).orElse(null);

        if (jurado != null && candidata != null) {
            Voto voto = new Voto();
            voto.setJurado(jurado);
            voto.setCandidata(candidata);
            // Se asignan los tres nuevos puntajes
            voto.setPuntaje1(puntaje1);
            voto.setPuntaje2(puntaje2);
            voto.setPuntaje3(puntaje3);
            // El subtotal se calculará automáticamente en la entidad Voto

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
        
        // Ordenar las candidatas por puntaje total de mayor a menor, manejando posibles nulos.
        List<Candidata> candidatasOrdenadas = candidatas.stream()
            .sorted(Comparator.comparing(Candidata::getPuntajeTotal, Comparator.nullsLast(Comparator.reverseOrder())))
            .collect(Collectors.toList());
            
        model.addAttribute("candidatas", candidatasOrdenadas);
        return "resultados";
    }

    /**
     * Nuevo endpoint para ejecutar el cálculo de los puntajes totales.
     * Debería ser accesible solo por un administrador.
     */
    @PostMapping("/resultados/calcular")
    public String calcularResultados(RedirectAttributes redirectAttributes) {
        try {
            candidataService.calcularPuntajesTotales();
            redirectAttributes.addFlashAttribute("successMessage", "Se han calculado y actualizado los puntajes totales.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ocurrió un error al calcular los puntajes.");
        }
        return "redirect:/resultados";
    }
    
    /**
     * Nuevo endpoint para mostrar el reporte completo del escrutinio.
     */
    @GetMapping("/escrutinio")
    public String mostrarEscrutinio(Model model) {
        List<Candidata> escrutinio = candidataService.generarEscrutinioCompleto();
        model.addAttribute("candidatas", escrutinio);
        return "escrutinio"; // Devuelve la plantilla HTML (escrutinio.html)
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
