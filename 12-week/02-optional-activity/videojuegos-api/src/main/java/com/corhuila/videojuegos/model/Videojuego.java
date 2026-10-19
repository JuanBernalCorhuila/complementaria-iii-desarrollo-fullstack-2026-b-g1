package com.corhuila.videojuegos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Videojuego {

    @Id
    @GeneratedValue
    private Long id;

    private String titulo;
    private String genero;
    private String plataforma;
    private double precio;
    private int anioLanzamiento;

    public Videojuego() {
    }

    public Videojuego(String titulo, String genero, String plataforma, double precio, int anioLanzamiento) {
        this.titulo = titulo;
        this.genero = genero;
        this.plataforma = plataforma;
        this.precio = precio;
        this.anioLanzamiento = anioLanzamiento;
    }

    // Getters y setters

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getAnioLanzamiento() {
        return anioLanzamiento;
    }

    public void setAnioLanzamiento(int anioLanzamiento) {
        this.anioLanzamiento = anioLanzamiento;
    }
}
