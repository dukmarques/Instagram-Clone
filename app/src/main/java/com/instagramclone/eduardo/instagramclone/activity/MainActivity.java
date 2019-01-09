package com.instagramclone.eduardo.instagramclone.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.instagramclone.eduardo.instagramclone.R;
import com.instagramclone.eduardo.instagramclone.fragment.FeedFragment;
import com.instagramclone.eduardo.instagramclone.fragment.NotificacoesFragment;
import com.instagramclone.eduardo.instagramclone.fragment.PerfilFragment;
import com.instagramclone.eduardo.instagramclone.fragment.PesquisaFragment;
import com.instagramclone.eduardo.instagramclone.fragment.PostagemFragment;
import com.instagramclone.eduardo.instagramclone.helper.ConfiguracaoFirebase;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configurar toolbar
        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        //toolbar.setTitle("Instagram");
        toolbar.setLogo(R.drawable.logo_nome);
        setSupportActionBar(toolbar);

        //Configurações de objetos
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();

        //Configuração do bottom navigation view
        configuraBottomNavigationView();
        //Configura feedFragment como padrão na inicialização do App
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.viewPager, new FeedFragment()).commit();
    }

    private void configuraBottomNavigationView(){
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavigation);

        //Configurações iniciais do Bottom navigation
        bottomNavigationViewEx.enableAnimation(true);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
        //bottomNavigationViewEx.setIconSize(33);

        //Habilitar navegação
        habilitarNavegacao(bottomNavigationViewEx);

        //Configura item selecionado inicialmente
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
    }

    /**
     * Método responsável por tratar eventos de click na BottomNavigation
     * @param viewEx
     */
    private void habilitarNavegacao(BottomNavigationViewEx viewEx){
        viewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch (menuItem.getItemId()){
                    case R.id.ic_home:
                        fragmentTransaction.replace(R.id.viewPager, new FeedFragment()).commit();
                        return true;

                    case R.id.ic_pesquisa:
                        fragmentTransaction.replace(R.id.viewPager, new PesquisaFragment()).commit();
                        return true;

                    case R.id.ic_postagem:
                        fragmentTransaction.replace(R.id.viewPager, new PostagemFragment()).commit();
                        return true;

                    case R.id.ic_notificacoes:
                        fragmentTransaction.replace(R.id.viewPager, new NotificacoesFragment()).commit();
                        return true;

                    case R.id.ic_perfil:
                        fragmentTransaction.replace(R.id.viewPager, new PerfilFragment()).commit();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sair:
                deslogarUsuario();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deslogarUsuario(){
        try {
            autenticacao.signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}