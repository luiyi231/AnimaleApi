package com.example.animalesgstionapicrud.activities;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetalleAnimalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_animal);

        Animal animal = (Animal) getIntent().getSerializableExtra("animal");
        if (animal != null) {
            mostrarDetallesAnimal(animal);
        }
    }

    private void mostrarDetallesAnimal(Animal animal) {
        // Configurar imagen según tipo
        ImageView ivAnimal = findViewById(R.id.ivAnimalDetalle);
        int iconResId = obtenerIconoPorTipo(animal);
        ivAnimal.setImageResource(iconResId);

        // Mostrar datos básicos
        ((TextView) findViewById(R.id.tvEspecie)).setText(animal.especie);
        ((TextView) findViewById(R.id.tvNombreCientifico)).setText(animal.nombreCientifico);
        ((TextView) findViewById(R.id.tvHabitad)).setText("Hábitat: " + animal.habitad);
        ((TextView) findViewById(R.id.tvPeso)).setText("Peso promedio: " + animal.pesoPromedio + " kg");
        ((TextView) findViewById(R.id.tvEstadoConservacion)).setText("Estado de conservación: " + animal.estadoConservacion);

        // Mostrar información específica según tipo
        LinearLayout layoutInfoEspecifica = findViewById(R.id.layoutInfoEspecifica);
        layoutInfoEspecifica.removeAllViews();

        if (animal instanceof Mamifero) {
            agregarDatoEspecifico(layoutInfoEspecifica, "Temperatura corporal", ((Mamifero) animal).temperaturaCorporal + "°C");
            agregarDatoEspecifico(layoutInfoEspecifica, "Tiempo gestación", ((Mamifero) animal).tiempoGestacion + " días");
            agregarDatoEspecifico(layoutInfoEspecifica, "Alimentación", ((Mamifero) animal).alimentacion);
        } else if (animal instanceof AveRapaz) {
            agregarDatoEspecifico(layoutInfoEspecifica, "Velocidad vuelo", ((AveRapaz) animal).velocidadVuelo + " km/h");
            agregarDatoEspecifico(layoutInfoEspecifica, "Tipo presa", ((AveRapaz) animal).tipoPresa);
            // Datos de Ave común
            agregarDatoEspecifico(layoutInfoEspecifica, "Envergadura alas", ((AveRapaz) animal).envergaduraAlas + " cm");
            agregarDatoEspecifico(layoutInfoEspecifica, "Color plumaje", ((AveRapaz) animal).colorPlumaje);
            agregarDatoEspecifico(layoutInfoEspecifica, "Tipo pico", ((AveRapaz) animal).tipoPico);
        } else if (animal instanceof Ave) {
            agregarDatoEspecifico(layoutInfoEspecifica, "Envergadura alas", ((Ave) animal).envergaduraAlas + " cm");
            agregarDatoEspecifico(layoutInfoEspecifica, "Color plumaje", ((Ave) animal).colorPlumaje);
            agregarDatoEspecifico(layoutInfoEspecifica, "Tipo pico", ((Ave) animal).tipoPico);
        }

        // Mostrar información adicional
        LinearLayout layoutDatosAdicionales = findViewById(R.id.layoutDatosAdicionales);
        layoutDatosAdicionales.removeAllViews();

        if (animal.informacionAdicional != null) {
            try {
                JSONObject infoAdicional = new JSONObject(animal.informacionAdicional);

                // Esperanza de vida
                if (infoAdicional.has("esperanzaVida")) {
                    ((TextView) findViewById(R.id.tvEsperanzaVida)).setText(
                            "Esperanza de vida: " + infoAdicional.getInt("esperanzaVida") + " años");
                }

                // Datos adicionales
                if (infoAdicional.has("datos")) {
                    JSONArray datosArray = infoAdicional.getJSONArray("datos");
                    for (int i = 0; i < datosArray.length(); i++) {
                        JSONObject dato = datosArray.getJSONObject(i);
                        agregarDatoAdicional(layoutDatosAdicionales,
                                dato.getString("nombreDato"),
                                dato.getString("valor"));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private int obtenerIconoPorTipo(Animal animal) {
        if (animal instanceof AveRapaz) return R.drawable.ave_rapaz;
        if (animal instanceof Ave) return R.drawable.pajaro;
        if (animal instanceof Mamifero) return R.drawable.mamiferos;
        return R.drawable.ni_idea;
    }

    private void agregarDatoEspecifico(LinearLayout layout, String nombre, String valor) {
        TextView tv = new TextView(this);
        tv.setText(nombre + ": " + valor);
        tv.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.addView(tv);
    }

    private void agregarDatoAdicional(LinearLayout layout, String nombre, String valor) {
        TextView tv = new TextView(this);
        tv.setText("• " + nombre + ": " + valor);
        tv.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setPadding(0, 4, 0, 4);
        layout.addView(tv);
    }
}