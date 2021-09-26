package com.silvandrade.chucknorrisio;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Menu;
import com.silvandrade.chucknorrisio.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

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