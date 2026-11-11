package com.tecnm.act3.controller;

import com.tecnm.act3.dto.CarreraResponse;
import com.tecnm.act3.entity.Carrera;
import com.tecnm.act3.service.CarreraService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/carreras")
public class CarreraViewController {

    private final CarreraService carreraService;

    public CarreraViewController(CarreraService carreraService) {
        this.carreraService = carreraService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("carreras", carreraService.listar().stream()
                .map(c -> CarreraResponse.from(c, carreraService.contarAlumnos(c.getId())))
                .toList());
        model.addAttribute("carrera", new Carrera());
        return "carreras/lista";
    }

    @PostMapping
    public String crear(@ModelAttribute("carrera") Carrera carrera, RedirectAttributes flash) {
        try {
            carreraService.crear(carrera);
            flash.addFlashAttribute("mensaje", "Carrera registrada correctamente");
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/carreras";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        try {
            carreraService.eliminar(id);
            flash.addFlashAttribute("mensaje", "Carrera eliminada correctamente");
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/carreras";
    }
}
