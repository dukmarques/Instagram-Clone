package com.instagramclone.eduardo.instagramclone.model;

import com.google.firebase.database.DatabaseReference;
import com.instagramclone.eduardo.instagramclone.helper.ConfiguracaoFirebase;

import java.util.HashMap;

public class PostagemCurtida {
    public Feed feed;
    public Usuario usuario;
    public int qtdCurtidas = 0;

    public PostagemCurtida() {
    }

    public void salvar(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();

        //Objeto usuário
        HashMap<String, Object> dadosUsuario = new HashMap<>();
        dadosUsuario.put("nomeUsuario", usuario.getNome());
        dadosUsuario.put("caminhoFoto", usuario.getCaminhoFoto());

        DatabaseReference pCurtidasRef = firebaseRef
                .child("postagens-cutidas")
                .child(feed.getId()) //Id_postagem
                .child(usuario.getId()); //id_usuário logado
        pCurtidasRef.setValue(dadosUsuario);

        //Atualizar quantidade de curtidas
        atualizarQtd(1);
    }

    public void atualizarQtd(int valor){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();

        DatabaseReference pCurtidasRef = firebaseRef
                .child("postagens-cutidas")
                .child(feed.getId())
                .child("qtdCurtidas");
        setQtdCurtidas(getQtdCurtidas() + valor);
        pCurtidasRef.setValue(getQtdCurtidas());
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getQtdCurtidas() {
        return qtdCurtidas;
    }

    public void setQtdCurtidas(int qtdCurtidas) {
        this.qtdCurtidas = qtdCurtidas;
    }
}