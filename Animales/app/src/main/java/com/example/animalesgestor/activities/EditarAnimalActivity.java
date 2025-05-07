package com.example.animalesgestor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.animalesgestor.R;
import com.example.animalesgestor.models.Animal;

import org.json.JSONObject;

public class EditarAnimalActivity extends AppCompatActivity {

    private Animal animal;
    private EditText etEspecie, etHabitad, etEsperanzaVida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_animal);

        // Recibir el animal a editar
        animal = (Animal) getIntent().getSerializableExtra("animal");

        // Configurar vistas
        etEspecie = findViewById(R.id.etEspecie);
        etHabitad = findViewById(R.id.etHabitad);
        etEsperanzaVida = findViewById(R.id.etEsperanzaVida);
        Button btnGuardar = findViewById(R.id.btnGuardar);

        // Cargar datos actuales
        etEspecie.setText(animal.especie);

        btnGuardar.setOnClickListener(v -> guardarCambios());
    }

    private void guardarCambios() {
        // Validar campo
        if (etEspecie.getText().toString().trim().isEmpty()) {
            etEspecie.setError("Ingrese un nombre");
            return;
        }
        if (etHabitad.getText().toString().trim().isEmpty()) {
            etHabitad.setError("Ingrese un Habitad");
            return;
        }
        if (etEsperanzaVida.getText().toString().trim().isEmpty()) {
            etEsperanzaVida.setError("Ingrese Esperanza Vida");
            return;
        }

        // Actualizar solo el nombre (puedes editar más campos)
        animal.especie = etEspecie.getText().toString();
        animal.habitad = etHabitad.getText().toString();
        try {
            // Obtener el JSON actual
            JSONObject info = new JSONObject(animal.informacionAdicional);

            // Convertir el texto del EditText a int
            int nuevaEsperanza = Integer.parseInt(etEsperanzaVida.getText().toString());

            // Actualizar el campo en el JSON
            info.put("esperanzaVida", nuevaEsperanza);

            // Guardar el JSON actualizado como String en el objeto
            animal.informacionAdicional = info.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }


        // Devolver el animal modificado
        Intent resultIntent = new Intent();
        resultIntent.putExtra("animalEditado", animal);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}