package com.tecnm.act3.repository;

import com.tecnm.act3.entity.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarreraRepository extends JpaRepository<Carrera, Long> {

    boolean existsByClave(String clave);
}
