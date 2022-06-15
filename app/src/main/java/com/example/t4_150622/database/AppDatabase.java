package com.example.t4_150622.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.t4_150622.dao.PeliculaDao;
import com.example.t4_150622.entities.Pelicula;

@Database(entities = {Pelicula.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PeliculaDao peliculaDao();

    public static AppDatabase getDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "database.peliculas.db")
                .allowMainThreadQueries()
                .build();
    }
}

