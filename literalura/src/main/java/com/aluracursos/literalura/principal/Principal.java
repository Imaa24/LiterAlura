package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    6 - Top 10 libros más descargados
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 -> buscarLibroWeb();
                case 2 -> listarLibrosRegistrados();
                case 3 -> listarAutoresRegistrados();
                case 4 -> listarAutoresVivos();
                case 5 -> listarLibrosPorIdioma();
                case 6 -> listarTop10Libros();
                case 0 -> System.out.println("Cerrando la aplicación...");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibroWeb() {
        System.out.println("Ingrese el título del libro que desea buscar:");
        var tituloLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, DatosGutendex.class);

        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            System.out.println("Libro encontrado ");
            System.out.println(libroBuscado.get());

            if(libroRepository.findByTitulo(libroBuscado.get().titulo()).isPresent()){
                System.out.println("El libro ya está registrado en la base de datos.");
                return;
            }

            DatosLibro datosLibro = libroBuscado.get();
            DatosAutor datosAutor = datosLibro.autores().get(0);

            Autor autor = autorRepository.findByNombre(datosAutor.nombre())
                    .orElseGet(() -> autorRepository.save(new Autor(datosAutor)));

            Libro libro = new Libro(datosLibro);
            libro.setAutor(autor);
            libroRepository.save(libro);
            System.out.println("Libro guardado exitosamente.");

        } else {
            System.out.println("Libro no encontrado");
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    private void listarAutoresVivos() {
        System.out.println("Ingrese el año vivo de autor(es) que desea buscar:");
        var anio = teclado.nextInt();
        List<Autor> autores = autorRepository.autoresVivosEnDeterminadoAnio(anio);
        autores.forEach(System.out::println);
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma para buscar los libros (es, en, fr, etc):");
        var idioma = teclado.nextLine();
        List<Libro> libros = libroRepository.findByIdioma(idioma);
        libros.forEach(System.out::println);
    }

    private void listarTop10Libros() {
        System.out.println("Top 10 Libros más descargados");
        List<Libro> libros = libroRepository.findTop10ByOrderByNumeroDeDescargasDesc();

        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados aún para calcular el top.");
        } else {
            libros.forEach(l -> System.out.println(
                    "Libro: " + l.getTitulo() +
                            " | Descargas: " + l.getNumeroDeDescargas() +
                            " | Autor: " + l.getAutor().getNombre()));
        }
    }
}