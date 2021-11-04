package com.silvandrade.sqlite_crud.database;

import android.provider.BaseColumns;

public class AlunosContract {

    public static abstract class AlumnoEntry implements BaseColumns {
        public static final String TABLE_NAME = "ALUNOS";
        public static final String ID = "ID";
        public static final String NOME = "NOME";
        public static final String SOBRENOME = "SOBRENOME";
        public static final String GRADE = "GRADE";
        public static final String GRUPO = "GRUPO";
        public static final String TURNO = "TURNO";
    }
}
