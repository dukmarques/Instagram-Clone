package com.instagramclone.eduardo.instagramclone.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.instagramclone.eduardo.instagramclone.R;
import com.zomato.photofilters.utils.ThumbnailItem;

import java.util.List;

public class AdapterMiniaturas extends RecyclerView.Adapter<AdapterMiniaturas.MyViewHolder> {
    private List<ThumbnailItem> listaFiltros;
    private Context context;

    public AdapterMiniaturas(List<ThumbnailItem> lista, Context c) {
        this.listaFiltros = lista;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_filtros, viewGroup, false);
        return new AdapterMiniaturas.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        ThumbnailItem item = listaFiltros.get(i);
        myViewHolder.foto.setImageBitmap(item.image);
        myViewHolder.nomeFiltro.setText(item.filterName);
    }

    @Override
    public int getItemCount() {
        return listaFiltros.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView foto;
        TextView nomeFiltro;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.imageFotoFiltro);
            nomeFiltro = itemView.findViewById(R.id.textNomeFiltro);
        }
    }
}