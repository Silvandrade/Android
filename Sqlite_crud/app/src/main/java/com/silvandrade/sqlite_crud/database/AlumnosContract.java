package com.silvandrade.sqlite_crud.database;

import android.provider.BaseColumns;

public class AlumnosContract {

    public static abstract class AlumnoEntry implements BaseColumns {
        public static final String TABLE_NAME = "ALUMNOS";
        public static final String ID = "ID";
        public static final String NOMBRE = "NOMBRE";
        public static final String APELLIDO = "APELLIDO";
        public static final String GRADO = "GRADO";
        public static final String GRUPO = "GRUPO";
        public static final String TURNO = "TURNO";
    }
}
