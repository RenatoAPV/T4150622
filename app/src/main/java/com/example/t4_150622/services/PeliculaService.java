package com.example.t4_150622.services;

import com.example.t4_150622.entities.Pelicula;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PeliculaService {
    @GET("peliculas")
    Call<List<Pelicula>> getPeliculas();

    @POST("peliculas")
    Call<Pelicula> create(@Body Pelicula pelicula);

    @PUT("peliculas/{id}")
    Call<Pelicula> update(@Path("id") int id, @Body Pelicula pelicula);
}
