package com.silvandrade.recyclerasync;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AsyncAdapterCategory adapterCategory;
    private ModelSource modelSource;
    private AdminDB adminDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_main);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(
                        this,
                        LinearLayoutManager.VERTICAL,
                        false
                )
        );

        adminDB = new AdminDB(this);
        modelSource = new DataSource(adminDB.getReadDB(), "Category");
        adapterCategory = new AsyncAdapterCategory(modelSource, recyclerView);
        recyclerView.setAdapter(adapterCategory);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapterCategory.onStart(recyclerView);
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapterCategory.onStop(recyclerView);
    }

    private void createAllDataBase(AdminDB db) {

        for(int i = 1; i <= 7; ++i) {

            Category category = new Category("Y", "Category" + i);
            db.insetDataTable(category, "Category");

            for(int j = 1; j <= 50; ++j) {
                Film film = new Film("X", "Título " + i, "Conteúdo " + i, i+"");
                db.insetDataTable(film, "Film");
            }
        }
    }
}