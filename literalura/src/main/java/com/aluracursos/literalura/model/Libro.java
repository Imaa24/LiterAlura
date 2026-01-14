package com.aluracursos.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ManyToOne
    private Autor autor;

    private String idioma;
    private Double numeroDeDescargas;

    public Libro(){}

    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        this.idioma = datosLibro.idiomas().get(0); // Primer idioma
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }

    @Override
    public String toString() {
        return "Libro: " + titulo + " | Autor: " + (autor != null ? autor.getNombre() : "N/A") +
                " | Idioma: " + idioma + " | Descargas: " + numeroDeDescargas;
    }

    public void setAutor(Autor autor) { this.autor = autor; }
    public String getTitulo() { return titulo; }
    public String getIdioma() { return idioma; }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Autor getAutor() {
        return autor;
    }
}