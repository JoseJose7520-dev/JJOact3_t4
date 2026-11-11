package com.tecnm.act3.controller;

import com.tecnm.act3.dto.AlumnoRequest;
import com.tecnm.act3.service.AlumnoService;
import com.tecnm.act3.service.CarreraService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// CRUD de alumnos con vistas Thymeleaf
@Controller
@RequestMapping("/alumnos")
public class AlumnoViewController {

    private final AlumnoService alumnoService;
    private final CarreraService carreraService;

    public AlumnoViewController(AlumnoService alumnoService, CarreraService carreraService) {
        this.alumnoService = alumnoService;
        this.carreraService = carreraService;
    }

    // READ: lista (opcionalmente filtrada por carrera)
    @GetMapping
    public String listar(@RequestParam(required = false) Long carreraId, Model model) {
        model.addAttribute("alumnos", alumnoService.listar(carreraId));
        model.addAttribute("carreras", carreraService.listar());
        model.addAttribute("carreraId", carreraId);
        return "alumnos/lista";
    }

    // READ: detalle de un alumno
    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("alumno", alumnoService.buscar(id));
        return "alumnos/detalle";
    }

    // CREATE: formulario vacío
    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("alumno", new AlumnoRequest());
        model.addAttribute("carreras", carreraService.listar());
        model.addAttribute("accion", "/alumnos/nuevo");
        model.addAttribute("tituloForm", "Registrar alumno");
        return "alumnos/formulario";
    }

    // CREATE: guardar
    @PostMapping("/nuevo")
    public String crear(@ModelAttribute("alumno") AlumnoRequest datos, Model model, RedirectAttributes flash) {
        try {
            alumnoService.crear(datos);
            flash.addFlashAttribute("mensaje", "Alumno registrado correctamente");
            return "redirect:/alumnos";
        } catch (RuntimeException e) {
            return volverAlFormulario(model, "/alumnos/nuevo", "Registrar alumno", e);
        }
    }

    // UPDATE: formulario con los datos actuales
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("alumno", alumnoService.aFormulario(alumnoService.buscar(id)));
        model.addAttribute("carreras", carreraService.listar());
        model.addAttribute("accion", "/alumnos/" + id + "/editar");
        model.addAttribute("tituloForm", "Editar alumno");
        return "alumnos/formulario";
    }

    // UPDATE: guardar cambios
    @PostMapping("/{id}/editar")
    public String actualizar(@PathVariable Long id, @ModelAttribute("alumno") AlumnoRequest datos,
                             Model model, RedirectAttributes flash) {
        try {
            alumnoService.actualizar(id, datos);
            flash.addFlashAttribute("mensaje", "Alumno actualizado correctamente");
            return "redirect:/alumnos";
        } catch (RuntimeException e) {
            return volverAlFormulario(model, "/alumnos/" + id + "/editar", "Editar alumno", e);
        }
    }

    // DELETE
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        alumnoService.eliminar(id);
        flash.addFlashAttribute("mensaje", "Alumno eliminado correctamente");
        return "redirect:/alumnos";
    }

    private String volverAlFormulario(Model model, String accion, String titulo, RuntimeException e) {
        model.addAttribute("carreras", carreraService.listar());
        model.addAttribute("accion", accion);
        model.addAttribute("tituloForm", titulo);
        model.addAttribute("error", "No se pudo guardar: revisa que la matrícula no esté repetida y que elegiste una carrera. (" + e.getMessage() + ")");
        return "alumnos/formulario";
    }
}
