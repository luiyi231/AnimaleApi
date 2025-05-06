package com.example.animalesgstionapicrud.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.animalesgstionapicrud.R;
import com.example.animalesgstionapicrud.Adapters.AnimalesAdapter;
import com.example.animalesgstionapicrud.models.Animal;
import com.example.animalesgstionapicrud.models.Ave;
import com.example.animalesgstionapicrud.models.AveRapaz;
import com.example.animalesgstionapicrud.models.Mamifero;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AnimalActivity extends AppCompatActivity implements AnimalesAdapter.OnAnimalesClickListener {

    private static final int REQUEST_CREAR_ANIMAL = 2;
    private RecyclerView rvAnimales;
    private AnimalesAdapter animalesAdapter;
    private List<Animal> animalesList;
    private ProgressBar progressBar;
    private Button btnCargar;
    private RequestQueue requestQueue;
    private final String BASE_URL = "https://raw.githubusercontent.com/adancondori/TareaAPI/refs/heads/main/api/animales.json";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal);

        rvAnimales = findViewById(R.id.rvAnimal);
        progressBar = findViewById(R.id.progressBar);
        btnCargar = findViewById(R.id.btnCargar);

        requestQueue = Volley.newRequestQueue(this);

        animalesList = new ArrayList<>();
        animalesAdapter = new AnimalesAdapter(animalesList, this, this);

        rvAnimales.setLayoutManager(new LinearLayoutManager(this));
        rvAnimales.setAdapter(animalesAdapter);

        cargarAnimales();
        FloatingActionButton fabAgregarAnimal = findViewById(R.id.fabAgregar); // Asegúrate de tener este FAB en tu layout
        fabAgregarAnimal.setOnClickListener(v -> {
            Intent intent = new Intent(this, CrearAnimalActivity.class);
            startActivityForResult(intent, REQUEST_CREAR_ANIMAL);
        });
    }

    private void cargarAnimales() {
        progressBar.setVisibility(View.VISIBLE);
        animalesList.clear();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                BASE_URL.trim(),
                null,
                response -> {
                    try {
                        JSONArray animalesArray = response.getJSONArray("animales");

                        for (int i = 0; i < animalesArray.length(); i++) {
                            JSONObject obj = animalesArray.getJSONObject(i);
                            String tipo = obj.getString("tipo");
                            Animal animal = null;

                            JSONObject infoAdicional = obj.getJSONObject("informacionAdicional");
                            String infoAdicionalString = infoAdicional.toString();

                            switch (tipo) {
                                case "Mamifero":
                                    Mamifero mamifero = new Mamifero(
                                            obj.getString("especie"),
                                            obj.getString("nombreCientifico"),
                                            obj.getString("habitat"),
                                            obj.getInt("pesoPromedio"),
                                            obj.getString("estadoConservacion"),
                                            obj.getDouble("temperaturaCorporal"),
                                            obj.getInt("tiempoGestacion"),
                                            obj.getString("alimentacion"),
                                            infoAdicionalString,
                                            tipo // Se añade el tipo
                                    );
                                    animal = mamifero;
                                    break;

                                case "Ave":
                                    Ave ave = new Ave(
                                            obj.getString("especie"),
                                            obj.getString("nombreCientifico"),
                                            obj.getString("habitat"),
                                            obj.getInt("pesoPromedio"),
                                            obj.getString("estadoConservacion"),
                                            infoAdicionalString,
                                            obj.getInt("envergaduraAlas"),
                                            obj.getString("colorPlumaje"),
                                            obj.getString("tipoPico"),
                                            tipo // Se añade el tipo
                                    );
                                    animal = ave;
                                    break;

                                case "AveRapaz":
                                    AveRapaz aveRapaz = new AveRapaz(
                                            obj.getString("especie"),
                                            obj.getString("nombreCientifico"),
                                            obj.getString("habitat"),
                                            obj.getInt("pesoPromedio"),
                                            obj.getString("estadoConservacion"),
                                            obj.getInt("envergaduraAlas"),
                                            obj.getString("colorPlumaje"),
                                            obj.getString("tipoPico"),
                                            obj.getInt("velocidadVuelo"),
                                            obj.getString("tipoPresa"),
                                            infoAdicionalString,
                                            tipo // Se añade el tipo
                                    );
                                    animal = aveRapaz;
                                    break;
                            }

                            if (animal != null) {
                                animalesList.add(animal);
                            }
                        }

                        animalesAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);

                    } catch (JSONException e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "Error al procesar JSON: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Error de red: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );

        requestQueue.add(request);
    }



    @Override
    public void onAnimalClick(Animal animal, int position) {
        Intent intent = new Intent(this, DetalleAnimalActivity.class);
        intent.putExtra("animal", animal);
        startActivity(intent);
    }

    private static final int REQUEST_EDITAR_ANIMAL = 1;
    private static final int REQUEST_AGREGAR_ANIMAL = 2;
    private int posicionEdicion = -1;

    @Override
    public void onEditClick(Animal animal, int position) {
        posicionEdicion = position; // Guardamos la posición para actualizar después

        Intent intent = new Intent(this, EditarAnimalActivity.class);
        intent.putExtra("animal", animal);
        startActivityForResult(intent, REQUEST_EDITAR_ANIMAL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_EDITAR_ANIMAL && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("animalEditado")) {
                Animal animalEditado = (Animal) data.getSerializableExtra("animalEditado");

                if (posicionEdicion != -1) {
                    // Actualizamos el animal en la lista
                    animalesList.set(posicionEdicion, animalEditado);

                    // Notificamos al adaptador del cambio
                    animalesAdapter.notifyItemChanged(posicionEdicion);

                    Toast.makeText(this, "Animal actualizado", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (requestCode == REQUEST_CREAR_ANIMAL && resultCode == RESULT_OK && data != null) {
            Animal nuevoAnimal = (Animal) data.getSerializableExtra("nuevoAnimal");
            if (nuevoAnimal != null) {
                // Agregar el nuevo animal a la lista
                animalesList.add(nuevoAnimal);

                // Notificar al adaptador
                animalesAdapter.notifyItemInserted(animalesList.size() - 1);

                // Opcional: Hacer scroll hasta el nuevo elemento
                rvAnimales.smoothScrollToPosition(animalesList.size() - 1);

                Toast.makeText(this, "Animal agregado", Toast.LENGTH_SHORT).show();
            }
        }else if (requestCode == REQUEST_CREAR_ANIMAL && resultCode == RESULT_OK && data != null) {
            Animal nuevoAnimal = (Animal) data.getSerializableExtra("nuevoAnimal");
            if (nuevoAnimal != null) {
                animalesList.add(nuevoAnimal);
                animalesAdapter.notifyItemInserted(animalesList.size() - 1);
                rvAnimales.smoothScrollToPosition(animalesList.size() - 1);
                Toast.makeText(this, "Animal agregado", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onDeleteClick(Animal animal, int position) {
        // Mostrar un diálogo de confirmación
        new AlertDialog.Builder(this)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de eliminar a " + animal.especie + "?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    // Eliminar el animal de la lista
                    animalesList.remove(position);

                    // Notificar al adaptador del cambio
                    animalesAdapter.notifyItemRemoved(position);

                    // Opcional: Actualizar las posiciones de los elementos restantes
                    animalesAdapter.notifyItemRangeChanged(position, animalesList.size());

                    Toast.makeText(this, animal.especie + " eliminado", Toast.LENGTH_SHORT).show();

                    // Aquí podrías añadir código para eliminar de tu API/Base de datos
                    // eliminarDeAPI(animal.id); // Si tienes un ID
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

}