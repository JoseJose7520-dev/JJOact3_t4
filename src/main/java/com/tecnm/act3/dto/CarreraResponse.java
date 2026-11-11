package com.tecnm.act3.dto;

import com.tecnm.act3.entity.Carrera;

public record CarreraResponse(Long id, String clave, String nombre, long totalAlumnos) {

    public static CarreraResponse from(Carrera c, long totalAlumnos) {
        return new CarreraResponse(c.getId(), c.getClave(), c.getNombre(), totalAlumnos);
    }
}
