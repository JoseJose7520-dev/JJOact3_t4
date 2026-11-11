package com.tecnm.act3.service;

import com.tecnm.act3.dto.AlumnoRequest;
import com.tecnm.act3.entity.Alumno;
import com.tecnm.act3.repository.AlumnoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// Lógica de negocio del CRUD de alumnos (el controlador solo recibe y responde).
@Service
public class AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final CarreraService carreraService;

    public AlumnoService(AlumnoRepository alumnoRepository, CarreraService carreraService) {
        this.alumnoRepository = alumnoRepository;
        this.carreraService = carreraService;
    }

    // READ
    public List<Alumno> listar(Long carreraId) {
        if (carreraId == null) {
            return alumnoRepository.findAll();
        }
        return alumnoRepository.findByCarreraId(carreraId);
    }

    public Alumno buscar(Long id) {
        return alumnoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe el alumno con id " + id));
    }

    // CREATE
    @Transactional
    public Alumno crear(AlumnoRequest datos) {
        Alumno alumno = new Alumno();
        copiarDatos(datos, alumno);
        return alumnoRepository.save(alumno);
    }

    // UPDATE
    @Transactional
    public Alumno actualizar(Long id, AlumnoRequest datos) {
        Alumno alumno = buscar(id);
        copiarDatos(datos, alumno);
        return alumnoRepository.save(alumno);
    }

    // DELETE
    @Transactional
    public void eliminar(Long id) {
        alumnoRepository.delete(buscar(id));
    }

    private void copiarDatos(AlumnoRequest datos, Alumno alumno) {
        if (datos.getCarreraId() == null) {
            throw new IllegalArgumentException("Selecciona una carrera");
        }
        alumno.setMatricula(datos.getMatricula());
        alumno.setNombre(datos.getNombre());
        alumno.setEmail(datos.getEmail());
        alumno.setSemestre(datos.getSemestre());
        alumno.setCarrera(carreraService.buscar(datos.getCarreraId()));
    }

    // Convierte un alumno guardado en los datos que necesita el formulario de edición
    public AlumnoRequest aFormulario(Alumno alumno) {
        AlumnoRequest datos = new AlumnoRequest();
        datos.setMatricula(alumno.getMatricula());
        datos.setNombre(alumno.getNombre());
        datos.setEmail(alumno.getEmail());
        datos.setSemestre(alumno.getSemestre());
        datos.setCarreraId(alumno.getCarrera().getId());
        return datos;
    }
}
