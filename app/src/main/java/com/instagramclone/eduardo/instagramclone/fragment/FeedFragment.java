package com.instagramclone.eduardo.instagramclone.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.instagramclone.eduardo.instagramclone.R;
import com.instagramclone.eduardo.instagramclone.adapter.AdapterFeed;
import com.instagramclone.eduardo.instagramclone.helper.ConfiguracaoFirebase;
import com.instagramclone.eduardo.instagramclone.helper.UsuarioFirebase;
import com.instagramclone.eduardo.instagramclone.model.Feed;
import com.instagramclone.eduardo.instagramclone.model.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {
    private RecyclerView recyclerFeed;
    private AdapterFeed adapterFeed;
    private List<Feed> listaFeed = new ArrayList<>();
    private ValueEventListener valueEventListenerFeed;
    private DatabaseReference feedRef;
    private String idUsuarioLogado;

    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_feed, container, false);

        //Configurações iniciais
        idUsuarioLogado = UsuarioFirebase.getIdentificadorUsuario();
        feedRef = ConfiguracaoFirebase.getFirebase()
                .child("feed")
                .child(idUsuarioLogado);

        //Inicializar Componentes
        adapterFeed = new AdapterFeed(listaFeed, getActivity());
        recyclerFeed = view.findViewById(R.id.recyclerFeed);

        //Configurar recyclerView
        recyclerFeed.setHasFixedSize(true);
        recyclerFeed.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerFeed.setAdapter(adapterFeed);

        return view;
    }

    private void listarFeed(){
        valueEventListenerFeed = feedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    listaFeed.add(ds.getValue(Feed.class));
                }
                Collections.reverse(listaFeed);
                adapterFeed.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        listarFeed();
    }

    @Override
    public void onStop() {
        super.onStop();
        feedRef.removeEventListener(valueEventListenerFeed);
    }
}