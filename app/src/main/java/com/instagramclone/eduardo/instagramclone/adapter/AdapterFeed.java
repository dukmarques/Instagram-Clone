package com.instagramclone.eduardo.instagramclone.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.instagramclone.eduardo.instagramclone.R;
import com.instagramclone.eduardo.instagramclone.helper.ConfiguracaoFirebase;
import com.instagramclone.eduardo.instagramclone.helper.UsuarioFirebase;
import com.instagramclone.eduardo.instagramclone.model.Feed;
import com.instagramclone.eduardo.instagramclone.model.PostagemCurtida;
import com.instagramclone.eduardo.instagramclone.model.Usuario;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterFeed extends RecyclerView.Adapter<AdapterFeed.MyViewHolder> {
    private List<Feed> listaFeed;
    private Context context;

    public AdapterFeed(List<Feed> listaFeed, Context context) {
        this.listaFeed = listaFeed;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_feed, viewGroup, false);
        return new AdapterFeed.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        final Feed feed = listaFeed.get(i);
        final Usuario usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();

        //Carrega dados do feed
        Uri uriFotoUsuario = Uri.parse(feed.getFotoUsuario());
        Uri uriFotoPostagem = Uri.parse(feed.getFotoPostagem());

        Glide.with(context).load(uriFotoUsuario).into(myViewHolder.fotoPerfil);
        Glide.with(context).load(uriFotoPostagem).into(myViewHolder.fotoPostagem);

        myViewHolder.descricao.setText(feed.getDescricao());
        myViewHolder.nome.setText(feed.getNomeUsuario());

        //Recuperar dados da postagem curtida
        DatabaseReference curtidasRef = ConfiguracaoFirebase.getFirebase()
                .child("postagens-curtidas")
                .child( feed.getId() );
        curtidasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int qtdCurtidas = 0;
                if( dataSnapshot.hasChild("qtdCurtidas") ){
                    PostagemCurtida postagemCurtida = dataSnapshot.getValue( PostagemCurtida.class );
                    qtdCurtidas = postagemCurtida.getQtdCurtidas();
                }

                //Verifica se já foi clicado
                if( dataSnapshot.hasChild( usuarioLogado.getId() ) ){
                    myViewHolder.likeButton.setLiked(true);
                }else {
                    myViewHolder.likeButton.setLiked(false);
                }

                //Monta objeto postagem curtida
                final PostagemCurtida curtida = new PostagemCurtida();
                curtida.setFeed( feed );
                curtida.setUsuario( usuarioLogado );
                curtida.setQtdCurtidas( qtdCurtidas );

                //Adiciona eventos para curtir uma foto
                myViewHolder.likeButton.setOnLikeListener(new OnLikeListener() {
                    @Override
                    public void liked(LikeButton likeButton) {
                        curtida.salvar();
                        myViewHolder.qtdCurtidas.setText( curtida.getQtdCurtidas() + " curtidas" );
                    }

                    @Override
                    public void unLiked(LikeButton likeButton) {
                        curtida.remover();
                        myViewHolder.qtdCurtidas.setText( curtida.getQtdCurtidas() + " curtidas" );
                    }
                });

                myViewHolder.qtdCurtidas.setText( curtida.getQtdCurtidas() + " curtidas" );

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listaFeed.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CircleImageView fotoPerfil;
        TextView nome, descricao, qtdCurtidas;
        ImageView fotoPostagem, visualizarComentario;
        LikeButton likeButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            fotoPerfil = itemView.findViewById(R.id.imagePerfilPostagem);
            fotoPostagem = itemView.findViewById(R.id.imagePostagemSelecionada);
            nome = itemView.findViewById(R.id.textPerfilPostagem);
            qtdCurtidas = itemView.findViewById(R.id.textQtdCurtidas);
            descricao = itemView.findViewById(R.id.textDescricaoPostagem);
            visualizarComentario = itemView.findViewById(R.id.imageComentarioFeed);
            likeButton = itemView.findViewById(R.id.likeButtonFeed);
        }
    }
}