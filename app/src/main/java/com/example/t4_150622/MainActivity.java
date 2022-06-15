package com.example.t4_150622;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.t4_150622.adapter.PeliculaAdapter;
import com.example.t4_150622.dao.PeliculaDao;
import com.example.t4_150622.database.AppDatabase;
import com.example.t4_150622.entities.Pelicula;
import com.example.t4_150622.factories.RetrofitFactory;
import com.example.t4_150622.services.PeliculaService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    List<Pelicula> peliculas = new ArrayList<>();

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getDatabase(getApplicationContext());

        Retrofit retrofit = RetrofitFactory.build();

        PeliculaService service = retrofit.create(PeliculaService.class);
        Call<List<Pelicula>> call = service.getPeliculas();

        call.enqueue(new Callback<List<Pelicula>>() {
            @Override
            public void onResponse(Call<List<Pelicula>> call, Response<List<Pelicula>> response) {
                if(response.isSuccessful()){
                    peliculas = response.body();
                    PeliculaDao dao = db.peliculaDao();
                    List<Pelicula> peliculasBD = dao.getAll();
                    int contWeb = peliculas.size();
                    int contBD = peliculasBD.size();
                    /*if(peliculasBD.isEmpty() || peliculasBD == null){
                        saveIndataBase(peliculas);
                    }*/
                    if(contWeb > contBD || contBD > contWeb){
                        dao.deletelist();
                        saveIndataBase(peliculas);
                    }
                    else{
                        PeliculaAdapter adapter = new PeliculaAdapter(peliculasBD);

                        RecyclerView rv = findViewById(R.id.rvPeliculas);
                        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        rv.setHasFixedSize(true);
                        rv.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Pelicula>> call, Throwable t) {
                Log.e("VJ_EX","Error de conectividad");
            }
        });

        FloatingActionButton fabButton = findViewById(R.id.fabCreate);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveIndataBase(List<Pelicula> peliculas){
        PeliculaDao dao = db.peliculaDao();
        for (Pelicula pelicula : peliculas){
            dao.create(pelicula);
        }
    }
}