package com.example.animalesgestor.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class Animal implements Serializable {
    public String especie;
    public String nombreCientifico;
    public String habitad;
    public int pesoPromedio;
    public String estadoConservacion;
    public String informacionAdicional; // Ahora almacenamos el JSON completo como String
    public String tipo;

    public Animal() {
        // Constructor vacío necesario para instanciación flexible
    }


    public Animal(String especie, String nombreCientifico, String habitad,
                  int pesoPromedio, String estadoConservacion,
                  String informacionAdicional, String tipo) {
        this.especie = especie;
        this.nombreCientifico = nombreCientifico;
        this.habitad = habitad;
        this.pesoPromedio = pesoPromedio;
        this.estadoConservacion = estadoConservacion;
        this.informacionAdicional = informacionAdicional;
        this.tipo = tipo;
    }


    // Método helper para obtener la esperanza de vida
    public int getEsperanzaVida() {
        try {
            JSONObject info = new JSONObject(informacionAdicional);
            return info.optInt("esperanzaVida", 0);
        } catch (Exception e) {
            return 0;
        }
    }

    // Método helper para obtener los datos adicionales
    public String getDatosAdicionales() {
        try {
            JSONObject info = new JSONObject(informacionAdicional);
            JSONArray datos = info.optJSONArray("datos");
            if (datos != null) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < datos.length(); i++) {
                    JSONObject dato = datos.getJSONObject(i);
                    sb.append("• ")
                            .append(dato.optString("nombreDato", ""))
                            .append(": ")
                            .append(dato.optString("valor", ""))
                            .append("\n");
                }
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "No hay datos adicionales";
    }


}
