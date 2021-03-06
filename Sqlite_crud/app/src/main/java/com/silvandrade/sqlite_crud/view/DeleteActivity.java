package com.silvandrade.sqlite_crud.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.silvandrade.sqlite_crud.R;
import com.silvandrade.sqlite_crud.controller.AlunosCursorAdapter;
import com.silvandrade.sqlite_crud.database.AdminDB;

public class DeleteActivity extends AppCompatActivity {

    ListView listViewDelete;
    EditText editTextDelete;
    Button buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        listViewDelete = (ListView) findViewById(R.id.list_view_delete);
        editTextDelete = (EditText) findViewById(R.id.edit_text_delete_id);
        buttonDelete = (Button) findViewById(R.id.button_delete_alumno);


        AdminDB adminDB = new AdminDB(getApplicationContext());
        AlunosCursorAdapter adapter = new AlunosCursorAdapter(getApplicationContext(), adminDB.getAllAlumnos(), 0);
        listViewDelete.setAdapter(adapter);

        buttonDelete.setOnClickListener(v -> {
            adminDB.deleteAlumno(editTextDelete.getText().toString());
            AlunosCursorAdapter newAdapter = new AlunosCursorAdapter(getApplicationContext(), adminDB.getAllAlumnos(), 0);
            listViewDelete.setAdapter(newAdapter);
            Toast.makeText(this, "Registro deletado com sucesso!", Toast.LENGTH_LONG).show();
        });
    }
}