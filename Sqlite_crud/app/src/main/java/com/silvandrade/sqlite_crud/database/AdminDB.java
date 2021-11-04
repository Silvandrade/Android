package com.silvandrade.sqlite_crud.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.silvandrade.sqlite_crud.database.AlunosContract.AlumnoEntry;
import com.silvandrade.sqlite_crud.model.Aluno;

import androidx.annotation.Nullable;

public class  AdminDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "escola_primaria.db";

    // Construtor do Sqlite
    public AdminDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); // Criando a base de dados.
    }


    // OnCreate do Sqlite
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + AlumnoEntry.TABLE_NAME + " ("
                + AlumnoEntry._ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + AlumnoEntry.ID + " TEXT NOT NULL,"
                + AlumnoEntry.NOME + " TEXT NOT NULL,"
                + AlumnoEntry.SOBRENOME + " TEXT NOT NULL,"
                + AlumnoEntry.GRADE + " TEXT NOT NULL,"
                + AlumnoEntry.GRUPO + " TEXT NOT NULL,"
                + AlumnoEntry.TURNO + " TEXT NOT NULL,"
                + "UNIQUE(" + AlumnoEntry.ID + "))");
    }

    // OnUpgrade Sqlite
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // db.insert(AlumnoEntry.TABLE_NAME, null, values);
    public long insertAlumno(SQLiteDatabase db, Aluno aluno) {
        return db.insert(
                AlumnoEntry.TABLE_NAME,
                null,
                aluno.toContentValues()
        );
    }

    // Atualizar um aluno.
    public long updateAlumno(String id, String name, String lastName, String degree, String group, String turn) {
        ContentValues values = new ContentValues();
        values.put(AlumnoEntry.NOME, name);
        values.put(AlumnoEntry.SOBRENOME, lastName);
        values.put(AlumnoEntry.GRADE, degree);
        values.put(AlumnoEntry.GRUPO, group);
        values.put(AlumnoEntry.TURNO, turn);

        return getReadableDatabase()
                .update(AlumnoEntry.TABLE_NAME,
                        values,
                        AlumnoEntry._ID + " = ?",
                        new String[]{id});
    }

    // Excluir um aluno.
    public long deleteAlumno(String id) {
        return getReadableDatabase()
                .delete(AlumnoEntry.TABLE_NAME,
                        AlumnoEntry._ID + " = ?",
                        new String[]{id});
    }

    // Buscar todos os alunos.
    public Cursor getAllAlumnos() {
        return getReadableDatabase()
                .query(
                        AlumnoEntry.TABLE_NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );
    }

    // Buscar um aluno.
    public Cursor getAlumnoById(String id) {
        return getReadableDatabase()
                .query(
                        AlumnoEntry.TABLE_NAME,
                        null,
                        AlumnoEntry._ID + " LIKE ?",
                        new String[]{id},
                        null,
                        null,
                        null
                );
    }
}
