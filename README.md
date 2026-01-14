LiterAlura es una aplicación de consola desarrollada en Java utilizando el framework Spring Boot. Su objetivo principal es permitir a los usuarios buscar libros, consultar información sobre autores y filtrar resultados mediante una conexión a la API de Gutendex y una base de datos PostgreSQL.

Este proyecto fue desarrollado como parte del desafío de Alura Latam para poner en práctica conocimientos sobre consumo de APIs, manejo de datos JSON y JPA/Hibernate.

## ✨ Funcionalidades

El sistema cuenta con un menú interactivo que permite:

1.  **🔎 Buscar libro por título:** Consulta la API externa, trae los datos y guarda el libro y su autor en la base de datos local si no existen.
2.  **📋 Listar libros registrados:** Muestra todos los libros que han sido buscados y guardados en la base de datos.
3.  **👥 Listar autores registrados:** Exhibe la lista de autores con sus años de nacimiento y fallecimiento.
4.  **📅 Listar autores vivos en un determinado año:** Permite ingresar un año y filtra qué autores estaban vivos en esa fecha (lógica compleja de DB).
5.  **🌍 Listar libros por idioma:** Filtra los libros guardados según el idioma (ES, EN, FR, PT, etc.).
6.  **🏆 Top 10 libros más descargados:** Muestra los 10 libros más populares de la base de datos ordenados por número de descargas.

## 🛠️ Tecnologías Utilizadas

* **Java 17** - Lenguaje de programación.
* **Spring Boot** - Framework para el desarrollo de la aplicación.
* **Spring Data JPA** - Para la persistencia de datos y creación de repositorios.
* **PostgreSQL** - Motor de base de datos.
* **Jackson** - Librería para el mapeo de datos JSON a Objetos Java (Deserialización).
* **Maven** - Gestor de dependencias.
* **Gutendex API** - API pública de libros.
