package com.instagramclone.eduardo.instagramclone.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.instagramclone.eduardo.instagramclone.R;
import com.instagramclone.eduardo.instagramclone.helper.UsuarioFirebase;
import com.instagramclone.eduardo.instagramclone.model.Usuario;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditarPerfilActivity extends AppCompatActivity {
    private CircleImageView imageEditarPerfil;
    private TextView textAlterarFoto;
    private TextInputEditText editNomePerfil, editEmailPerfil;
    private Button buttonSalvarAlteracoes;
    private Usuario usuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        //Configurações iniciais
        usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();

        //Configurar toolbar
        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Editar Perfil");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        //Inicializar Componentes
        inicializarComponentes();

        //Recuperar os dados do usuário
        final FirebaseUser usuarioPerfil = UsuarioFirebase.getUsuarioAtual();
        editNomePerfil.setText(usuarioPerfil.getDisplayName());
        editEmailPerfil.setText(usuarioPerfil.getEmail());

        //Salvar alterações do nome
        buttonSalvarAlteracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeAtualiazado = editNomePerfil.getText().toString();

                //Atualizar nome no perfil
                UsuarioFirebase.atualizarNomeUsuario(nomeAtualiazado);

                //Atualizar nome no banco de dados
                usuarioLogado.setNome(nomeAtualiazado);
                usuarioLogado.atualizar();

                Toast.makeText(EditarPerfilActivity.this, "Dados atualizado", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void inicializarComponentes(){
        imageEditarPerfil = findViewById(R.id.imagePerfil);
        textAlterarFoto = findViewById(R.id.textAlterarFoto);
        editNomePerfil = findViewById(R.id.editNomePerfil);
        editEmailPerfil = findViewById(R.id.editEmailPerfil);
        buttonSalvarAlteracoes = findViewById(R.id.buttonSalvarAlteracoes);
        editEmailPerfil.setFocusable(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}