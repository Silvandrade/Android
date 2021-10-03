package com.silvandrade.chucknorrisio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.silvandrade.chucknorrisio.databinding.ActivityMainBinding;
import com.silvandrade.chucknorrisio.datasource.CategoryRemoteDataSource;
import com.silvandrade.chucknorrisio.model.CategoryItem;
import com.silvandrade.chucknorrisio.presentation.CategoryPresenter;
import com.xwray.groupie.GroupAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private GroupAdapter adapter;
    private CategoryPresenter presenter;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawerLayout = binding.drawerLayout; // Pegou a referência do Layout que adiciona o Menu (NavigationView).

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, binding.appBarMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle); // Toggle serve para abrir e fechar o menu.
        toggle.syncState();

        NavigationView navigationView = binding.navView; // Referência a meu objeto.
        navigationView.setNavigationItemSelectedListener(this); // Passo para o metodo de escuta do objeto a classe que implementou a interface de escuta.

        RecyclerView rvMain = findViewById(R.id.rv_main);

        adapter = new GroupAdapter();
        adapter.setOnItemClickListener((item, view) -> { // Abrindo Activity_Joke e passando dados para exibição.
            final Intent intent = new Intent(MainActivity.this, JokeActivity.class);
            CategoryItem categoryItem = (CategoryItem) item; // Convertendo o valor recebido.
            intent.putExtra(JokeActivity.CATEGORY_KEY, categoryItem.getCategoryName()); // Chave e valor da categoria clicada.
            startActivity(intent); // Iniciando a outra activity.
        });

        rvMain.setAdapter(adapter);
        rvMain.setLayoutManager(new LinearLayoutManager(this));

        CategoryRemoteDataSource dataSource = new CategoryRemoteDataSource();
        presenter = new CategoryPresenter(this, dataSource);
        presenter.requestAll();

    }

    public void showProgressDialog() {
        if(progress == null){
            progress = new ProgressDialog(this);
            progress.setMessage(getString(R.string.loading));
            progress.setIndeterminate(true); // Tempo de exibição indeterminado.
            progress.setCancelable(false); // Para que ninguém cancele.
            progress.show();
        }
    }

    public void hideProgressDialog() {
        if(progress != null) {
            progress.hide();
        }
    }

    public void showCategories(List<CategoryItem> items) {
        adapter.addAll(items);
        adapter.notifyDataSetChanged();
    }

    public void showFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() { // Define as ações a clicar no botão voltar da barra de tarefas.
        DrawerLayout drawerLayout = binding.drawerLayout;

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) { // Se o menu estiver aberto.
            drawerLayout.closeDrawer(GravityCompat.START); // Feche o menu.
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) { // Escuta de evento de clique na NavigationView.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        }

        DrawerLayout drawerLayout = binding.drawerLayout; // Pega o layout que adiciona o menu (NavigationView).
        drawerLayout.closeDrawer(GravityCompat.START); // Fecha o NavigationView que contém o menu.
        return true;
    }
}