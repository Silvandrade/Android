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

    private Button buttonMainCreate;
    private Button buttonMainQuery;
    private Button buttonMainUpdate;
    private Button buttonMainDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textViewMainName = (TextView) findViewById(R.id.text_view_main_name);

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
    }
}