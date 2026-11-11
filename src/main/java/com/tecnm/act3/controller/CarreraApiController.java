package com.tecnm.act3.controller;

import com.tecnm.act3.dto.AlumnoResponse;
import com.tecnm.act3.dto.CarreraResponse;
import com.tecnm.act3.entity.Carrera;
import com.tecnm.act3.service.AlumnoService;
import com.tecnm.act3.service.CarreraService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carreras")
public class CarreraApiController {

    private final CarreraService carreraService;
    private final AlumnoService alumnoService;

    public CarreraApiController(CarreraService carreraService, AlumnoService alumnoService) {
        this.carreraService = carreraService;
        this.alumnoService = alumnoService;
    }

    @GetMapping
    public List<CarreraResponse> listar() {
        return carreraService.listar().stream()
                .map(c -> CarreraResponse.from(c, carreraService.contarAlumnos(c.getId())))
                .toList();
    }

    // Relación desde el lado "uno": los alumnos de una carrera
    @GetMapping("/{id}/alumnos")
    public List<AlumnoResponse> alumnosDeCarrera(@PathVariable Long id) {
        carreraService.buscar(id);
        return alumnoService.listar(id).stream().map(AlumnoResponse::from).toList();
    }

    @PostMapping
    public ResponseEntity<CarreraResponse> crear(@RequestBody Carrera carrera) {
        Carrera guardada = carreraService.crear(carrera);
        return ResponseEntity.status(HttpStatus.CREATED).body(CarreraResponse.from(guardada, 0));
    }
}
