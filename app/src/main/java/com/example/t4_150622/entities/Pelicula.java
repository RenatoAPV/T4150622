package com.example.t4_150622.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pelicula")
public class Pelicula {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "nombre")
    public String titulo;
    @ColumnInfo(name = "descripcion")
    public String sinopsis;
    @ColumnInfo(name = "link")
    public String imagen;

    public Pelicula() {
    }

    public Pelicula(int id, String titulo, String sinopsis, String imagen) {
        this.id = id;
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.imagen = imagen;
    }
}