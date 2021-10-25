package com.silvandrade.sqlite_crud.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.silvandrade.sqlite_crud.R;
import com.silvandrade.sqlite_crud.database.AdminDB;

public class MainActivity extends AppCompatActivity {

    private TextView textViewMainName;
    private TextView textViewMainGroup;
    private Button buttonMainCreate;
    private Button buttonMainQuery;
    private Button buttonMainUpdate;
    private Button buttonMainDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewMainName = (TextView) findViewById(R.id.text_view_main_name);

        buttonMainCreate = (Button) findViewById(R.id.button_main_create);
        buttonMainQuery = (Button) findViewById(R.id.button_main_read);
        buttonMainUpdate = (Button) findViewById(R.id.button_main_update);
        buttonMainDelete = (Button) findViewById(R.id.button_main_delete);

        executeDataBase();

        buttonMainCreate.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, InsertActivity.class));
        });

        buttonMainQuery.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, QueryActivity.class));
        });

        buttonMainUpdate.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, UpdateActivity.class));
        });

        buttonMainDelete.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, DeleteActivity.class));
        });
    }

    public void executeDataBase() {
        // Executar a base de dados.
        AdminDB adminDB = new AdminDB(MainActivity.this);
        SQLiteDatabase database = adminDB.getWritableDatabase();


        /*
          //--------------------SELECT BANDO DE DADOS--------------------------

        // Mêtodo 1
        //database.query(null, null, null, null, null, null, null);
        String[] columns = {"NOMBRE"};
        Cursor cursor1 = database.query("ALUMNOS", columns, "ID = 1", null,null, null, null);

        while (cursor1.moveToNext()) {
            String name = cursor1.getString((int) cursor1.getColumnIndex("NOMBRE"));
            textViewMainName.setText(name);
        }

        //Mêtodo 2
        //database.rawQuery(null, null);
//        Cursor cursor2 = database.rawQuery("SELECT * " +
//                                "FROM ALUMNOS " +
//                                "WHERE ID = 1", null); // Mêtodo 2
//
//        while(cursor2.moveToNext()) {
//            String name = cursor2.getString((int) cursor2.getColumnIndex("NOMBRE"));
//            textViewMain.setText(name);
//        }



          //--------------------UPDATE BANDO DE DADOS--------------------------

        database.execSQL("UPDATE ALUMNOS SET GRUPO = 'B' WHERE ID = 1"); // Update database.

        // SELECT database.
        String[] columnGroup = {"GRUPO"};
        Cursor cursor3 = database.query("ALUMNOS", columnGroup, "ID = 1", null, null, null, null, null );

        while(cursor3.moveToNext()) {
            String group = cursor3.getString((int) cursor3.getColumnIndex(columnGroup[0]));
            textViewMainGroup.setText(group);
        }


          //--------------------DELETE BANDO DE DADOS--------------------------


        database.execSQL("DELETE FROM ALUMNOS WHERE ID = 2");
        */
    }
}