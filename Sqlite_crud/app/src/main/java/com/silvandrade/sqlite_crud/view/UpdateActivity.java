package com.silvandrade.sqlite_crud.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.silvandrade.sqlite_crud.R;
import com.silvandrade.sqlite_crud.database.AdminDB;

public class UpdateActivity extends AppCompatActivity {

    private Button buttonUpdate;
    private EditText editTextId;
    private EditText editTextNombre;
    private EditText editTextApellido;
    private EditText editTextGrupo;
    private EditText editTextGrado;
    private EditText editTextTurno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        buttonUpdate = (Button) findViewById(R.id.button_update);
        editTextId = (EditText) findViewById(R.id.edit_text_view_update_id);
        editTextNombre = (EditText) findViewById(R.id.edit_text_view_update_nombre);
        editTextApellido = (EditText) findViewById(R.id.edit_text_view_update_apellido);
        editTextGrado = (EditText) findViewById(R.id.edit_text_view_update_grado);
        editTextGrupo = (EditText) findViewById(R.id.edit_text_view_update_grupo);
        editTextTurno = (EditText) findViewById(R.id.edit_text_view_update_turno);

        AdminDB adminDB = new AdminDB(getApplicationContext());

        buttonUpdate.setOnClickListener(v -> {
            adminDB.updateAlumno(
                    editTextId.getText().toString(),
                    editTextNombre.getText().toString(),
                    editTextApellido.getText().toString(),
                    editTextGrado.getText().toString(),
                    editTextGrupo.getText().toString(),
                    editTextTurno.getText().toString()
            );
        });
    }
}