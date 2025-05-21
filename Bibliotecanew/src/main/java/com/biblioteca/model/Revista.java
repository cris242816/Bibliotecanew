package com.biblioteca.model;

public class Revista {
    private int id;
    private String titulo;
    private String editor;
    private String genero;
    private int anioPublicacion;
    private int numero;
    private int paginas;
    private boolean disponible;

    public Revista() {}

    public Revista(String titulo, String editor, String genero, int anioPublicacion, int numero, int paginas) {
        this.titulo = titulo;
        this.editor = editor;
        this.genero = genero;
        this.anioPublicacion = anioPublicacion;
        this.numero = numero;
        this.paginas = paginas;
        this.disponible = true;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getEditor() { return editor; }
    public void setEditor(String editor) { this.editor = editor; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public int getAnioPublicacion() { return anioPublicacion; }
    public void setAnioPublicacion(int anioPublicacion) { this.anioPublicacion = anioPublicacion; }
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }
    public int getPaginas() { return paginas; }
    public void setPaginas(int paginas) { this.paginas = paginas; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public String toString() {
        return titulo + " (N°" + numero + ", " + anioPublicacion + ")";
    }
}