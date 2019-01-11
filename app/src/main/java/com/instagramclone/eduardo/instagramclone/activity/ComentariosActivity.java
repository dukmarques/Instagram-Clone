package com.instagramclone.eduardo.instagramclone.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.instagramclone.eduardo.instagramclone.R;
import com.instagramclone.eduardo.instagramclone.helper.UsuarioFirebase;
import com.instagramclone.eduardo.instagramclone.model.Comentario;
import com.instagramclone.eduardo.instagramclone.model.Usuario;

public class ComentariosActivity extends AppCompatActivity {
    private EditText editComentario;

    private String idPostagem;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);

        //Inicializar Componentes
        editComentario = findViewById(R.id.editComentario);

        //Configurações iniciais
        usuario = UsuarioFirebase.getDadosUsuarioLogado();

        //Configurar toolbar
        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Comentários");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        //Recupera id da postagem
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            idPostagem = bundle.getString("idPostagem");
        }
    }

    public void salvarComentário(View view){
        String textoComentario = editComentario.getText().toString();
        if (textoComentario != null && !textoComentario.equals("")){
            Comentario comentario = new Comentario();
            comentario.setIdPostagem(idPostagem);
            comentario.setIdUsuario(usuario.getId());
            comentario.setNomeUsuario(usuario.getNome());
            comentario.setCaminhoFoto(usuario.getCaminhoFoto());
            comentario.setComentario(textoComentario);
            
            if (comentario.salvar()){
                Toast.makeText(this, "Comentário salvo", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Escreva um comentário", Toast.LENGTH_SHORT).show();
        }

        //Limpar comentário digitado
        editComentario.setText("");
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return false;
    }
}