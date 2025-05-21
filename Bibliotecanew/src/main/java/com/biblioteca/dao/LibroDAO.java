package com.biblioteca.dao;

import com.biblioteca.model.Libro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {
    private Connection conexion;

    public LibroDAO() {
        conexion = ConexionBD.getConexion();
    }

    public void agregarLibro(Libro libro) {
        String sql = "INSERT INTO libros (titulo, autor, genero, anio_publicacion, paginas) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getGenero());
            ps.setInt(4, libro.getAnioPublicacion());
            ps.setInt(5, libro.getPaginas());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                libro.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Libro> obtenerTodosLibros() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                libros.add(mapearLibro(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }

    public List<Libro> buscarPorTitulo(String titulo) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE titulo LIKE ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, "%" + titulo + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                libros.add(mapearLibro(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }

    public List<Libro> buscarPorAutor(String autor) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE autor LIKE ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, "%" + autor + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                libros.add(mapearLibro(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }

    public List<Libro> buscarPorGenero(String genero) {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE genero LIKE ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, "%" + genero + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                libros.add(mapearLibro(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }

    public List<Libro> buscarCombinado(String titulo, String autor, String genero) {
        List<Libro> libros = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM libros WHERE ");
        List<String> condiciones = new ArrayList<>();
        List<String> parametros = new ArrayList<>();

        if (!titulo.isEmpty()) {
            condiciones.add("titulo LIKE ?");
            parametros.add("%" + titulo + "%");
        }
        if (!autor.isEmpty()) {
            condiciones.add("autor LIKE ?");
            parametros.add("%" + autor + "%");
        }
        if (!genero.isEmpty()) {
            condiciones.add("genero LIKE ?");
            parametros.add("%" + genero + "%");
        }

        if (condiciones.isEmpty()) {
            return obtenerTodosLibros();
        }

        sql.append(String.join(" AND ", condiciones));

        try (PreparedStatement ps = conexion.prepareStatement(sql.toString())) {
            for (int i = 0; i < parametros.size(); i++) {
                ps.setString(i + 1, parametros.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                libros.add(mapearLibro(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }

    public List<String> obtenerGenerosUnicos() {
        List<String> generos = new ArrayList<>();
        String sql = "SELECT DISTINCT genero FROM libros";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                generos.add(rs.getString("genero"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return generos;
    }

    public void actualizarLibro(Libro libro) {
        String sql = "UPDATE libros SET titulo = ?, autor = ?, genero = ?, anio_publicacion = ?, paginas = ?, disponible = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getGenero());
            ps.setInt(4, libro.getAnioPublicacion());
            ps.setInt(5, libro.getPaginas());
            ps.setBoolean(6, libro.isDisponible());
            ps.setInt(7, libro.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarLibro(int id) {
        String sql = "DELETE FROM libros WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Libro obtenerLibroPorId(int id) {
        String sql = "SELECT * FROM libros WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapearLibro(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Libro mapearLibro(ResultSet rs) throws SQLException {
        Libro libro = new Libro();
        libro.setId(rs.getInt("id"));
        libro.setTitulo(rs.getString("titulo"));
        libro.setAutor(rs.getString("autor"));
        libro.setGenero(rs.getString("genero"));
        libro.setAnioPublicacion(rs.getInt("anio_publicacion"));
        libro.setPaginas(rs.getInt("paginas"));
        libro.setDisponible(rs.getBoolean("disponible"));
        return libro;
    }
}