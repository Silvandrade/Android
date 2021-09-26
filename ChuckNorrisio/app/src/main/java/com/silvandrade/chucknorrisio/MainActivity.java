package com.silvandrade.chucknorrisio;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import com.silvandrade.chucknorrisio.databinding.ActivityMainBinding;
import com.silvandrade.chucknorrisio.model.CategoryItem;
import com.xwray.groupie.GroupAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private GroupAdapter adapter;

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
        rvMain.setAdapter(adapter);
        rvMain.setLayoutManager(new LinearLayoutManager(this));
        populateItem(); // Chamando o método para preencher a lista.
    }

    private void populateItem() {
        List<CategoryItem> items = new ArrayList<>();

        items.add(new CategoryItem("cat1", 0xFF00FFFF));
        items.add(new CategoryItem("cat2", 0xFFA0FFFF));
        items.add(new CategoryItem("cat3", 0xFF0AFFFF));
        items.add(new CategoryItem("cat4", 0xFF00F5FF));
        items.add(new CategoryItem("cat5", 0xFF00F1FF));
        items.add(new CategoryItem("cat6", 0xFF004FFF));
        items.add(new CategoryItem("cat7", 0xFF00FFFF));
        items.add(new CategoryItem("cat8", 0xFF003FFF));
        items.add(new CategoryItem("cat9", 0xFF00FF7F));
        items.add(new CategoryItem("cat10", 0xF800FFFF));
        items.add(new CategoryItem("cat11", 0xF000FFFF));

        adapter.addAll(items); // Pode adicionar uma coleção que extende de ViewHolder.
        adapter.notifyDataSetChanged(); // Notificar surgimento de novos dados.
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