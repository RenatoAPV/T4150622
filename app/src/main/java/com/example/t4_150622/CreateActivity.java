package com.example.t4_150622;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.t4_150622.entities.Pelicula;
import com.example.t4_150622.factories.RetrofitFactory;
import com.example.t4_150622.services.PeliculaService;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1000;

    static final int REQUEST_CAMERA_PERMISSION = 100;
    String imagen;
    ImageView fotoLibro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button btnTomarFoto = findViewById(R.id.btnTomarFoto);

        fotoLibro = findViewById(R.id.ivPelicularRegistro);
        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                } else {
                    requestPermissions(new String[] {Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                }
            }
        });
        EditText etTitulo = findViewById(R.id.etPeliculaTitulo);
        EditText etSinopsis = findViewById(R.id.etPeliculaSinopsis);

        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = RetrofitFactory.build();

                PeliculaService service = retrofit.create(PeliculaService.class);

                Pelicula pelicula = new Pelicula();
                pelicula.titulo = String.valueOf(etTitulo.getText());
                pelicula.sinopsis = String.valueOf(etSinopsis.getText());
                pelicula.imagen = imagen;



                Call<Pelicula> call = service.create(pelicula);

                call.enqueue(new Callback<Pelicula>() {
                    @Override
                    public void onResponse(Call<Pelicula> call, Response<Pelicula> response) {
                        if(response.isSuccessful()){
                            Log.i("registro", new Gson().toJson(response.body()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Pelicula> call, Throwable t) {
                        Log.i("registro", "Error registro");
                    }

                });
            }
        });
    }

    public static String convertS(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imagen = convertS(imageBitmap);
            fotoLibro.setImageBitmap(imageBitmap);

        }
    }
}