package com.example.animalesgstionapicrud.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.animalesgstionapicrud.R;
import com.example.animalesgstionapicrud.models.Animal;
import com.example.animalesgstionapicrud.models.Ave;
import com.example.animalesgstionapicrud.models.AveRapaz;
import com.example.animalesgstionapicrud.models.Mamifero;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CrearAnimalActivity extends AppCompatActivity {

    private Spinner spAnimalType;
    private TextInputEditText etEspecie, etNombreCientifico, etHabitat, etPeso, etEsperanzaVida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_animal);

        // Inicializar vistas
        spAnimalType = findViewById(R.id.spAnimalType);
        etEspecie = findViewById(R.id.etEspecie);
        etNombreCientifico = findViewById(R.id.etNombreCientifico);
        etHabitat = findViewById(R.id.etHabitat);
        etPeso = findViewById(R.id.etPeso);
        etEsperanzaVida = findViewById(R.id.etEsperanzaVida);

        // Configurar spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.tipos_animal, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAnimalType.setAdapter(adapter);

        // Botón Limpiar
        findViewById(R.id.btnLimpiar).setOnClickListener(v -> limpiarFormulario());

        // Botón Guardar
        findViewById(R.id.btnGuardar).setOnClickListener(v -> crearAnimal());
    }

    private void limpiarFormulario() {
        etEspecie.setText("");
        etNombreCientifico.setText("");
        etHabitat.setText("");
        etPeso.setText("");
        etEsperanzaVida.setText("");
        spAnimalType.setSelection(0);
    }

    private void crearAnimal() {
        // Validar campos
        if (!validarCampos()) {
            return;
        }

        // Crear objeto JSON para información adicional
        JSONObject infoAdicional = new JSONObject();
        try {
            infoAdicional.put("esperanzaVida", Integer.parseInt(etEsperanzaVida.getText().toString()));
            infoAdicional.put("datos", new JSONArray()); // Array vacío para datos adicionales
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Crear el animal según el tipo seleccionado
        Animal nuevoAnimal = crearAnimalSegunTipo(infoAdicional.toString());

        // Devolver el nuevo animal
        Intent resultIntent = new Intent();
        resultIntent.putExtra("nuevoAnimal", nuevoAnimal);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private Animal crearAnimalSegunTipo(String infoAdicional) {
        String tipo = spAnimalType.getSelectedItem().toString();
        String especie = etEspecie.getText().toString();
        String nombreCientifico = etNombreCientifico.getText().toString();
        String habitat = etHabitat.getText().toString();
        int peso = Integer.parseInt(etPeso.getText().toString());

        switch (tipo) {
            case "Mamífero":
                return new Mamifero(
                        especie,
                        nombreCientifico,
                        habitat,
                        peso,
                        "Desconocido", // estadoConservacion por defecto
                        0.0, // temperaturaCorporal por defecto
                        0, // tiempoGestacion por defecto
                        "Desconocido", // alimentacion por defecto
                        infoAdicional,
                        tipo
                );
            case "Ave":
                return new Ave(
                        especie,
                        nombreCientifico,
                        habitat,
                        peso,
                        "Desconocido", // estadoConservacion por defecto
                        infoAdicional,
                        0, // envergaduraAlas por defecto
                        "Desconocido", // colorPlumaje por defecto
                        "Desconocido",
                        tipo// tipoPico por defecto
                );
            case "Ave Rapaz":
                return new AveRapaz(
                        especie,
                        nombreCientifico,
                        habitat,
                        peso,
                        "Desconocido", // estadoConservacion por defecto
                        0, // envergaduraAlas por defecto
                        "Desconocido", // colorPlumaje por defecto
                        "Desconocido", // tipoPico por defecto
                        0, // velocidadVuelo por defecto
                        "Desconocido", // tipoPresa por defecto
                        infoAdicional,
                        tipo
                );
            default:
                return null;
        }
    }

    private boolean validarCampos() {
        boolean valido = true;

        if (etEspecie.getText().toString().trim().isEmpty()) {
            etEspecie.setError("Campo requerido");
            valido = false;
        }

        if (etPeso.getText().toString().trim().isEmpty()) {
            etPeso.setError("Campo requerido");
            valido = false;
        }

        return valido;
    }
}