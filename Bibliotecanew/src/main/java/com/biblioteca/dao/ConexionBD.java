package com.biblioteca.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConexionBD {
    private static Connection conexion = null;
    private static final String SCRIPT_CREACION =
            "CREATE DATABASE IF NOT EXISTS biblioteca;" +
                    "USE biblioteca;" +
                    "CREATE TABLE IF NOT EXISTS libros (" +
                    "    id INT AUTO_INCREMENT PRIMARY KEY," +
                    "    titulo VARCHAR(100) NOT NULL," +
                    "    autor VARCHAR(100) NOT NULL," +
                    "    genero VARCHAR(50) NOT NULL," +
                    "    anio_publicacion INT NOT NULL," +
                    "    paginas INT NOT NULL," +
                    "    disponible BOOLEAN DEFAULT TRUE" +
                    ");" +
                    "CREATE TABLE IF NOT EXISTS dvds (" +
                    "    id INT AUTO_INCREMENT PRIMARY KEY," +
                    "    titulo VARCHAR(100) NOT NULL," +
                    "    director VARCHAR(100) NOT NULL," +
                    "    genero VARCHAR(50) NOT NULL," +
                    "    anio_publicacion INT NOT NULL," +
                    "    duracion_minutos INT NOT NULL," +
                    "    disponible BOOLEAN DEFAULT TRUE" +
                    ");" +
                    "CREATE TABLE IF NOT EXISTS revistas (" +
                    "    id INT AUTO_INCREMENT PRIMARY KEY," +
                    "    titulo VARCHAR(100) NOT NULL," +
                    "    editor VARCHAR(100) NOT NULL," +
                    "    genero VARCHAR(50) NOT NULL," +
                    "    anio_publicacion INT NOT NULL," +
                    "    numero INT NOT NULL," +
                    "    paginas INT NOT NULL," +
                    "    disponible BOOLEAN DEFAULT TRUE" +
                    ");" +
                    "INSERT IGNORE INTO libros (titulo, autor, genero, anio_publicacion, paginas) VALUES " +
                    "('Cien años de soledad', 'Gabriel García Márquez', 'Realismo mágico', 1967, 432)," +
                    "('1984', 'George Orwell', 'Ciencia ficción', 1949, 328)," +
                    "('El Principito', 'Antoine de Saint-Exupéry', 'Fábula', 1943, 96)," +
                    "('Don Quijote de la Mancha', 'Miguel de Cervantes', 'Novela', 1605, 863)," +
                    "('Orgullo y prejuicio', 'Jane Austen', 'Romance', 1813, 432)," +
                    "('Crimen y castigo', 'Fiódor Dostoyevski', 'Ficción psicológica', 1866, 551)," +
                    "('El Hobbit', 'J.R.R. Tolkien', 'Fantasía', 1937, 310)," +
                    "('Ulises', 'James Joyce', 'Modernista', 1922, 730)," +
                    "('Moby Dick', 'Herman Melville', 'Aventura', 1851, 635)," +
                    "('Guerra y Paz', 'León Tolstói', 'Histórica', 1869, 1225)," +
                    "('Los miserables', 'Victor Hugo', 'Histórica', 1862, 1463)," +
                    "('El retrato de Dorian Gray', 'Oscar Wilde', 'Ficción gótica', 1890, 254)," +
                    "('Drácula', 'Bram Stoker', 'Terror', 1897, 418)," +
                    "('Fahrenheit 451', 'Ray Bradbury', 'Ciencia ficción', 1953, 249)," +
                    "('El señor de los anillos', 'J.R.R. Tolkien', 'Fantasía', 1954, 1178)," +
                    "('Matar a un ruiseñor', 'Harper Lee', 'Ficción sureña', 1960, 376)," +
                    "('El código Da Vinci', 'Dan Brown', 'Misterio', 2003, 454)," +
                    "('Las crónicas de Narnia', 'C.S. Lewis', 'Fantasía', 1950, 767)," +
                    "('El alquimista', 'Paulo Coelho', 'Ficción', 1988, 208)," +
                    "('La sombra del viento', 'Carlos Ruiz Zafón', 'Misterio', 2001, 487);" +
                    "INSERT IGNORE INTO dvds (titulo, director, genero, anio_publicacion, duracion_minutos) VALUES " +
                    "('El Padrino', 'Francis Ford Coppola', 'Drama', 1972, 175)," +
                    "('Pulp Fiction', 'Quentin Tarantino', 'Crimen', 1994, 154)," +
                    "('El Señor de los Anillos: El Retorno del Rey', 'Peter Jackson', 'Fantasía', 2003, 201)," +
                    "('Forrest Gump', 'Robert Zemeckis', 'Drama', 1994, 142)," +
                    "('Matrix', 'Lana y Lilly Wachowski', 'Ciencia ficción', 1999, 136)," +
                    "('Titanic', 'James Cameron', 'Romance', 1997, 194)," +
                    "('Interestelar', 'Christopher Nolan', 'Ciencia ficción', 2014, 169)," +
                    "('El Caballero Oscuro', 'Christopher Nolan', 'Acción', 2008, 152)," +
                    "('Origen', 'Christopher Nolan', 'Ciencia ficción', 2010, 148)," +
                    "('Gladiador', 'Ridley Scott', 'Acción', 2000, 155)," +
                    "('El Silencio de los Inocentes', 'Jonathan Demme', 'Thriller', 1991, 118)," +
                    "('Parásitos', 'Bong Joon-ho', 'Drama', 2019, 132)," +
                    "('El Laberinto del Fauno', 'Guillermo del Toro', 'Fantasía', 2006, 118)," +
                    "('Shrek', 'Andrew Adamson', 'Animación', 2001, 90)," +
                    "('Los Vengadores: Endgame', 'Anthony y Joe Russo', 'Acción', 2019, 181)," +
                    "('El Rey León', 'Roger Allers', 'Animación', 1994, 88)," +
                    "('Jurassic Park', 'Steven Spielberg', 'Aventura', 1993, 127)," +
                    "('La La Land', 'Damien Chazelle', 'Musical', 2016, 128)," +
                    "('Whiplash', 'Damien Chazelle', 'Drama', 2014, 106)," +
                    "('El Gran Hotel Budapest', 'Wes Anderson', 'Comedia', 2014, 99);" +
                    "INSERT IGNORE INTO revistas (titulo, editor, genero, anio_publicacion, numero, paginas) VALUES " +
                    "('National Geographic', 'National Geographic Society', 'Ciencia', 2023, 1, 120)," +
                    "('Time', 'Time USA', 'Noticias', 2023, 15, 80)," +
                    "('Scientific American', 'Springer Nature', 'Ciencia', 2023, 4, 90)," +
                    "('The Economist', 'The Economist Group', 'Economía', 2023, 12, 110)," +
                    "('Vogue', 'Condé Nast', 'Moda', 2023, 3, 150)," +
                    "('Rolling Stone', 'Penske Media', 'Música', 2023, 2, 70)," +
                    "('Forbes', 'Forbes Media', 'Negocios', 2023, 5, 95)," +
                    "('Wired', 'Condé Nast', 'Tecnología', 2023, 6, 85)," +
                    "('People', 'Meredith Corporation', 'Entretenimiento', 2023, 8, 60)," +
                    "('Sports Illustrated', 'Maven Coalition', 'Deportes', 2023, 7, 75)," +
                    "('The New Yorker', 'Condé Nast', 'Cultura', 2023, 9, 100)," +
                    "('Reader\\'s Digest', 'Trusted Media Brands', 'General', 2023, 10, 65)," +
                    "('Cosmopolitan', 'Hearst Communications', 'Moda', 2023, 11, 130)," +
                    "('GQ', 'Condé Nast', 'Estilo', 2023, 12, 110)," +
                    "('Esquire', 'Hearst Communications', 'Estilo', 2023, 13, 95)," +
                    "('Popular Science', 'Recurrent Ventures', 'Ciencia', 2023, 14, 55)," +
                    "('PC Magazine', 'Ziff Davis', 'Tecnología', 2023, 15, 70)," +
                    "('Architectural Digest', 'Condé Nast', 'Diseño', 2023, 16, 125)," +
                    "('Car and Driver', 'Hearst Communications', 'Automóviles', 2023, 17, 85)," +
                    "('Bon Appétit', 'Condé Nast', 'Cocina', 2023, 18, 115);";

    private ConexionBD() {
        try {
            // Cargar configuración desde el archivo properties
            InputStream input = ConexionBD.class.getClassLoader().getResourceAsStream("database.properties");
            Properties prop = new Properties();
            prop.load(input);

            String url = prop.getProperty("db.url");
            String usuario = prop.getProperty("db.user");
            String contrasena = prop.getProperty("db.password");

            // Establecer conexión
            conexion = DriverManager.getConnection(url, usuario, contrasena);
            System.out.println("Conexión establecida con éxito");

            // Crear tablas e insertar datos iniciales
            crearTablasYDatosIniciales();
        } catch (IOException | SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void crearTablasYDatosIniciales() {
        try (Statement stmt = conexion.createStatement()) {
            // Dividir el script por sentencias individuales
            String[] sentencias = SCRIPT_CREACION.split(";");

            for (String sentencia : sentencias) {
                if (!sentencia.trim().isEmpty()) {
                    stmt.execute(sentencia + ";");
                }
            }
            System.out.println("Tablas creadas y datos iniciales insertados correctamente");
        } catch (SQLException e) {
            System.err.println("Error al crear tablas o insertar datos iniciales: " + e.getMessage());
        }
    }

    public static Connection getConexion() {
        if (conexion == null) {
            new ConexionBD();
        }
        return conexion;
    }

    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}