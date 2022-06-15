package com.example.t4_150622;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.t4_150622.database.AppDatabase;
import com.example.t4_150622.entities.Pelicula;
import com.example.t4_150622.factories.RetrofitFactory;
import com.example.t4_150622.services.PeliculaService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditarPelicula extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pelicula);
        String animeJson = getIntent().getStringExtra("Pelicula");
        Pelicula pelicula = new Gson().fromJson(animeJson, Pelicula.class);

        EditText etNombreAct = findViewById(R.id.etTituloPeliculaAct);
        EditText etSinopsisACt = findViewById(R.id.etSinopsisPeliculaAct);
        EditText etUrlAct = findViewById(R.id.etPeliculaURL);

        Button btnAct = findViewById(R.id.btnActualizarDatos);
        btnAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = RetrofitFactory.build();
                Pelicula anime2 = new Pelicula();
                PeliculaService service = retrofit.create(PeliculaService.class);

                anime2.titulo = String.valueOf(etNombreAct.getText());
                anime2.sinopsis = String.valueOf(etSinopsisACt.getText());
                anime2.imagen = String.valueOf(etUrlAct.getText());

                AppDatabase db = AppDatabase.getDatabase(getApplicationContext());

                Call<Pelicula> call = service.update(pelicula.id,anime2);
                call.enqueue(new Callback<Pelicula>() {
                    @Override
                    public void onResponse(Call<Pelicula> call, Response<Pelicula> response) {
                        if(response.isSuccessful()){
                            Log.i("Actualizar", new Gson().toJson(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Pelicula> call, Throwable t) {

                    }
                });
            }
        });

    }
}