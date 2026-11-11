package com.tecnm.act3.repository;

import com.tecnm.act3.entity.Alumno;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    List<Alumno> findByCarreraId(Long carreraId);

    long countByCarreraId(Long carreraId);
}
