package com.silvandrade.sqlite_crud.view;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.silvandrade.sqlite_crud.R;
import com.silvandrade.sqlite_crud.database.AdminDB;
import com.silvandrade.sqlite_crud.model.Aluno;

public class InsertActivity extends AppCompatActivity {

    private Button buttonInsert;
    private EditText editTextId;
    private EditText editTextNombre;
    private EditText editTextApellido;
    private EditText editTextGrupo;
    private EditText editTextGrado;
    private EditText editTextTurno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        buttonInsert = (Button) findViewById(R.id.button_insert);
        editTextId = (EditText) findViewById(R.id.edit_text_view_insert_id);
        editTextNombre = (EditText) findViewById(R.id.edit_text_view_insert_nome);
        editTextApellido = (EditText) findViewById(R.id.edit_text_view_insert_sobrenome);
        editTextGrado = (EditText) findViewById(R.id.edit_text_view_insert_grade);
        editTextGrupo = (EditText) findViewById(R.id.edit_text_view_insert_grupo);
        editTextTurno = (EditText) findViewById(R.id.edit_text_view_insert_turno);

        buttonInsert.setOnClickListener(v -> {

            AdminDB adminDB = new AdminDB(getApplicationContext());
            SQLiteDatabase db = adminDB.getWritableDatabase();

            adminDB.insertAlumno(db,
                new Aluno(
                        editTextId.getText().toString(),
                        editTextNombre.getText().toString(),
                        editTextApellido.getText().toString(),
                        editTextGrado.getText().toString(),
                        editTextGrupo.getText().toString(),
                        editTextTurno.getText().toString()
                )
            );

            Toast.makeText(this,"Registro salvo com sucesso!", Toast.LENGTH_LONG).show();
        });
    }
}