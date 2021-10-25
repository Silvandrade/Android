package com.silvandrade.sqlite_crud.model;

import android.content.ContentValues;

import com.silvandrade.sqlite_crud.database.AlumnosContract;

public class Alumno {

    private String id;
    private String nombre;
    private String apellido;
    private String grado;
    private String grupo;
    private String turno;

    public Alumno(String id, String name, String lastname, String degree, String group, String turn) {
        this.id = id;
        this.nombre = name;
        this.apellido = lastname;
        this.grado = degree;
        this.grupo = group;
        this.turno = turn;
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AlumnosContract.AlumnoEntry.ID, String.valueOf(this.id));
        contentValues.put(AlumnosContract.AlumnoEntry.NOMBRE, this.nombre.toUpperCase());
        contentValues.put(AlumnosContract.AlumnoEntry.APELLIDO, this.apellido.toUpperCase());
        contentValues.put(AlumnosContract.AlumnoEntry.GRADO, String.valueOf(this.grado));
        contentValues.put(AlumnosContract.AlumnoEntry.GRUPO, this.grupo.toUpperCase());
        contentValues.put(AlumnosContract.AlumnoEntry.TURNO, this.turno.toUpperCase());
        return contentValues;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}
