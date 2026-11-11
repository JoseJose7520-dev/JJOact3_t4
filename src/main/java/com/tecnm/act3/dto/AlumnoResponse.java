package com.tecnm.act3.dto;

import com.tecnm.act3.entity.Alumno;

// Lo que regresa la API: incluye el NOMBRE de la carrera, no solo su id.
public record AlumnoResponse(
        Long id,
        String matricula,
        String nombre,
        String email,
        int semestre,
        Long carreraId,
        String carreraClave,
        String carreraNombre) {

    public static AlumnoResponse from(Alumno a) {
        return new AlumnoResponse(
                a.getId(), a.getMatricula(), a.getNombre(), a.getEmail(), a.getSemestre(),
                a.getCarrera().getId(), a.getCarrera().getClave(), a.getCarrera().getNombre());
    }
}
