package com.biblioteca.model;

public class Dvd {
    private int id;
    private String titulo;
    private String director;
    private String genero;
    private int anioPublicacion;
    private int duracionMinutos;
    private boolean disponible;

    public Dvd() {}

    public Dvd(String titulo, String director, String genero, int anioPublicacion, int duracionMinutos) {
        this.titulo = titulo;
        this.director = director;
        this.genero = genero;
        this.anioPublicacion = anioPublicacion;
        this.duracionMinutos = duracionMinutos;
        this.disponible = true;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public int getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(int anioPublicacion) { this.anioPublicacion = anioPublicacion; }
    public int getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(int duracionMinutos) { this.duracionMinutos = duracionMinutos; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public String toString() {
        return titulo + " (" + director + ", " + anioPublicacion + ")";
    }
}