package com.tecnm.act3.dto;

// Datos que llegan al crear o editar un alumno (formulario o JSON).
// La carrera se manda solo con su id.
public class AlumnoRequest {

    private String matricula;
    private String nombre;
    private String email;
    private int semestre;
    private Long carreraId;

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public int getSemestre() { return semestre; }
    public void setSemestre(int semestre) { this.semestre = semestre; }

    public Long getCarreraId() { return carreraId; }
    public void setCarreraId(Long carreraId) { this.carreraId = carreraId; }
}
