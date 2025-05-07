package com.example.animalesgestor.models;

public class Ave extends Animal {
    public int envergaduraAlas;
    public String colorPlumaje;
    public String tipoPico;

    public Ave(String especie, String nombreCientifico, String habitad,
               int pesoPromedio, String estadoConservacion,
               String informacionAdicional, // Cambiado a String
               int envergaduraAlas, String colorPlumaje, String tipoPico, String tipo ) {
        super(especie, nombreCientifico, habitad, pesoPromedio,
                estadoConservacion, informacionAdicional, tipo); // Ahora recibe String
        this.envergaduraAlas = envergaduraAlas;
        this.colorPlumaje = colorPlumaje;
        this.tipoPico = tipoPico;
    }
}
