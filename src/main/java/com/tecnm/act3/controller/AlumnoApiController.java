package com.tecnm.act3.controller;

import com.tecnm.act3.dto.AlumnoRequest;
import com.tecnm.act3.dto.AlumnoResponse;
import com.tecnm.act3.service.AlumnoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// CRUD REST de alumnos (JSON) para probar con Postman o Bruno
@RestController
@RequestMapping("/api/alumnos")
public class AlumnoApiController {

    private final AlumnoService alumnoService;

    public AlumnoApiController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    @GetMapping
    public List<AlumnoResponse> listar(@RequestParam(required = false) Long carreraId) {
        return alumnoService.listar(carreraId).stream().map(AlumnoResponse::from).toList();
    }

    @GetMapping("/{id}")
    public AlumnoResponse buscar(@PathVariable Long id) {
        return AlumnoResponse.from(alumnoService.buscar(id));
    }

    @PostMapping
    public ResponseEntity<AlumnoResponse> crear(@RequestBody AlumnoRequest datos) {
        return ResponseEntity.status(HttpStatus.CREATED).body(AlumnoResponse.from(alumnoService.crear(datos)));
    }

    @PutMapping("/{id}")
    public AlumnoResponse actualizar(@PathVariable Long id, @RequestBody AlumnoRequest datos) {
        return AlumnoResponse.from(alumnoService.actualizar(id, datos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        alumnoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
