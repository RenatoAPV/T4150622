package com.example.t4_150622;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.t4_150622.entities.Pelicula;
import com.example.t4_150622.factories.RetrofitFactory;
import com.example.t4_150622.services.PeliculaService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PeliculaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pelicula);

        String animeJson = getIntent().getStringExtra("Pelicula");
        Pelicula pelicula = new Gson().fromJson(animeJson, Pelicula.class);

        TextView tvTitulo = findViewById(R.id.tvTituloPelicula);
        TextView tvSinopsis = findViewById(R.id.tvSinopsisPelicula);
        ImageView ivPelicula = findViewById(R.id.ivPeliculaDetalle);

        ivPelicula.setImageBitmap(convertB(pelicula.imagen));
        tvTitulo.setText(pelicula.titulo);
        tvSinopsis.setText(pelicula.sinopsis);

        Button btnEditar = findViewById(R.id.btnEditar);
        Button btnEliminar = findViewById(R.id.btnEliminar);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditarPelicula.class);

                String animeJson = new Gson().toJson(pelicula);
                intent.putExtra("Pelicula", animeJson);

                startActivity(intent);
            }
        });
    }
    public static Bitmap convertB(String base64Str) throws IllegalArgumentException
    {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",")  + 1),
                Base64.DEFAULT
        );

        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}