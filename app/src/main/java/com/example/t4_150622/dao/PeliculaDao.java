package com.example.t4_150622.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.t4_150622.entities.Pelicula;

import java.util.List;

@Dao
public interface PeliculaDao {
    @Query("SELECT * FROM pelicula")
    List<Pelicula> getAll();

    @Query("SELECT * FROM pelicula WHERE id = :id")
    Pelicula find(int id);

    @Insert
    void create(Pelicula pelicula);

    @Query("DELETE FROM pelicula")
    void deletelist();
}
