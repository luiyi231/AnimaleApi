package com.example.animalesgstionapicrud.models;

public class Mamifero extends Animal {
    public Double temperaturaCorporal;
    public int tiempoGestacion;
    public String alimentacion;

    public Mamifero(String especie, String nombreCientifico, String habitad,
                    int pesoPromedio, String estadoConservacion,
                    Double temperaturaCorporal, int tiempoGestacion,
                    String alimentacion,
                    String informacionAdicional, String tipo ) { // Cambiado a String
        super(especie, nombreCientifico, habitad, pesoPromedio,
                estadoConservacion, informacionAdicional, tipo); // String en lugar de int
        this.temperaturaCorporal = temperaturaCorporal;
        this.tiempoGestacion = tiempoGestacion;
        this.alimentacion = alimentacion;
    }
}
