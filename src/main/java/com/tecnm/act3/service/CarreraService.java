package com.tecnm.act3.service;

import com.tecnm.act3.entity.Carrera;
import com.tecnm.act3.repository.AlumnoRepository;
import com.tecnm.act3.repository.CarreraRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarreraService {

    private final CarreraRepository carreraRepository;
    private final AlumnoRepository alumnoRepository;

    public CarreraService(CarreraRepository carreraRepository, AlumnoRepository alumnoRepository) {
        this.carreraRepository = carreraRepository;
        this.alumnoRepository = alumnoRepository;
    }

    public List<Carrera> listar() {
        return carreraRepository.findAll();
    }

    public Carrera buscar(Long id) {
        return carreraRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No existe la carrera con id " + id));
    }

    public long contarAlumnos(Long carreraId) {
        return alumnoRepository.countByCarreraId(carreraId);
    }

    @Transactional
    public Carrera crear(Carrera carrera) {
        if (carreraRepository.existsByClave(carrera.getClave())) {
            throw new IllegalArgumentException("Ya existe una carrera con la clave " + carrera.getClave());
        }
        carrera.setId(null);
        return carreraRepository.save(carrera);
    }

    @Transactional
    public void eliminar(Long id) {
        Carrera carrera = buscar(id);
        if (alumnoRepository.countByCarreraId(id) > 0) {
            throw new IllegalStateException("No se puede eliminar " + carrera.getNombre() + ": tiene alumnos inscritos");
        }
        carreraRepository.delete(carrera);
    }
}
