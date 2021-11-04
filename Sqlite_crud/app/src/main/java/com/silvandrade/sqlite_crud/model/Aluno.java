package com.silvandrade.sqlite_crud.model;

import android.content.ContentValues;

import com.silvandrade.sqlite_crud.database.AlunosContract;

public class Aluno {

    private String id;
    private String nome;
    private String sobrenome;
    private String grade;
    private String grupo;
    private String turno;

    public Aluno(String id, String name, String lastname, String degree, String group, String turn) {
        this.id = id;
        this.nome = name;
        this.sobrenome = lastname;
        this.grade = degree;
        this.grupo = group;
        this.turno = turn;
    }

    public ContentValues toContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AlunosContract.AlumnoEntry.ID, String.valueOf(this.id));
        contentValues.put(AlunosContract.AlumnoEntry.NOME, this.nome.toUpperCase());
        contentValues.put(AlunosContract.AlumnoEntry.SOBRENOME, this.sobrenome.toUpperCase());
        contentValues.put(AlunosContract.AlumnoEntry.GRADE, String.valueOf(this.grade));
        contentValues.put(AlunosContract.AlumnoEntry.GRUPO, this.grupo.toUpperCase());
        contentValues.put(AlunosContract.AlumnoEntry.TURNO, this.turno.toUpperCase());
        return contentValues;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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
