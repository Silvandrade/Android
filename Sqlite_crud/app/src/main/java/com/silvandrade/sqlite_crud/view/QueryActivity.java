package com.silvandrade.sqlite_crud.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.silvandrade.sqlite_crud.R;
import com.silvandrade.sqlite_crud.controller.AlumnosCursorAdapter;
import com.silvandrade.sqlite_crud.database.AdminDB;

public class QueryActivity extends AppCompatActivity {

    ListView listViewQuery;
    EditText editTextQuery;
    Button buttonQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        listViewQuery = (ListView) findViewById(R.id.list_view_query);
        editTextQuery = (EditText) findViewById(R.id.edit_text_query_id);
        buttonQuery = (Button) findViewById(R.id.button_query_alumno);

        AdminDB adminDB = new AdminDB(getApplicationContext());
        AlumnosCursorAdapter adapterAlumnos = new AlumnosCursorAdapter(QueryActivity.this, adminDB.getAllAlumnos(), 0);
        listViewQuery.setAdapter(adapterAlumnos);

        buttonQuery.setOnClickListener(v -> {
            listViewQuery.setAdapter(new AlumnosCursorAdapter(
                    QueryActivity.this,
                    adminDB.getAlumnoById(editTextQuery.getText().toString()),
                    0));
        });
    }
}