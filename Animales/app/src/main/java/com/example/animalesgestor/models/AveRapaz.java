package com.example.animalesgestor.models;

public class AveRapaz extends Ave {
    public int velocidadVuelo;
    public String tipoPresa;

    public AveRapaz(String especie, String nombreCientifico, String habitad,
                    int pesoPromedio, String estadoConservacion,
                    int envergaduraAlas, String colorPlumaje, String tipoPico,
                    int velocidadVuelo, String tipoPresa,
                    String informacionAdicional, String tipo) { // Cambiado a String
        super(especie, nombreCientifico, habitad, pesoPromedio,
                estadoConservacion, informacionAdicional, // String en lugar de int
                envergaduraAlas, colorPlumaje, tipoPico, tipo);
        this.velocidadVuelo = velocidadVuelo;
        this.tipoPresa = tipoPresa;
    }
}
