package com.tecnm.act3.config;

import com.tecnm.act3.entity.Carrera;
import com.tecnm.act3.repository.CarreraRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// Si la tabla de carreras está vacía, inserta algunas para poder empezar a registrar alumnos.
@Component
public class DatosIniciales implements CommandLineRunner {

    private final CarreraRepository carreraRepository;

    public DatosIniciales(CarreraRepository carreraRepository) {
        this.carreraRepository = carreraRepository;
    }

    @Override
    public void run(String... args) {
        if (carreraRepository.count() > 0) {
            return;
        }
        carreraRepository.save(new Carrera("ISC", "Ingeniería en Sistemas Computacionales"));
        carreraRepository.save(new Carrera("IGE", "Ingeniería en Gestión Empresarial"));
        carreraRepository.save(new Carrera("IC", "Ingeniería Civil"));
        carreraRepository.save(new Carrera("IE", "Ingeniería Eléctrica"));
    }
}
