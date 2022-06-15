package com.example.t4_150622.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t4_150622.PeliculaActivity;
import com.example.t4_150622.R;
import com.example.t4_150622.entities.Pelicula;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.PeliculaViewHolder> {
    List<Pelicula> peliculas;
    public PeliculaAdapter(List<Pelicula> peliculas){
        this.peliculas = peliculas;
    }


    @NonNull
    @Override
    public PeliculaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new PeliculaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeliculaViewHolder vh, int position) {
        View itemView = vh.itemView;

        Pelicula pelicula = peliculas.get(position);
        TextView tvTitulo = vh.itemView.findViewById(R.id.tvTituloP);
        TextView tvSinopsis = vh.itemView.findViewById(R.id.tvSinopsisP);
        tvTitulo.setText(pelicula.titulo);
        tvSinopsis.setText(pelicula.sinopsis);
        ImageView imageavatar = vh.itemView.findViewById(R.id.ivPelicula);

        Picasso.get().load(pelicula.imagen).into(imageavatar);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(itemView.getContext(), PeliculaActivity.class);

                String animeJson = new Gson().toJson(pelicula);
                intent.putExtra("Pelicula", animeJson);

                itemView.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return peliculas.size();
    }

    class PeliculaViewHolder extends RecyclerView.ViewHolder{

        public PeliculaViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
