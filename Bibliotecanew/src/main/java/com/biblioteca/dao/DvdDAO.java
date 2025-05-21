package com.biblioteca.dao;

import com.biblioteca.model.Dvd;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DvdDAO {
    private Connection conexion;

    public DvdDAO() {
        conexion = ConexionBD.getConexion();
    }

    public void agregarDvd(Dvd dvd) {
        String sql = "INSERT INTO dvds (titulo, director, genero, anio_publicacion, duracion_minutos) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, dvd.getTitulo());
            ps.setString(2, dvd.getDirector());
            ps.setString(3, dvd.getGenero());
            ps.setInt(4, dvd.getAnioPublicacion());
            ps.setInt(5, dvd.getDuracionMinutos());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                dvd.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Dvd> obtenerTodosDvds() {
        List<Dvd> dvds = new ArrayList<>();
        String sql = "SELECT * FROM dvds";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                dvds.add(mapearDvd(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dvds;
    }

    public List<Dvd> buscarPorTitulo(String titulo) {
        List<Dvd> dvds = new ArrayList<>();
        String sql = "SELECT * FROM dvds WHERE titulo LIKE ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, "%" + titulo + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dvds.add(mapearDvd(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dvds;
    }

    public List<Dvd> buscarPorDirector(String director) {
        List<Dvd> dvds = new ArrayList<>();
        String sql = "SELECT * FROM dvds WHERE director LIKE ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, "%" + director + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dvds.add(mapearDvd(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dvds;
    }

    public List<Dvd> buscarPorGenero(String genero) {
        List<Dvd> dvds = new ArrayList<>();
        String sql = "SELECT * FROM dvds WHERE genero LIKE ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, "%" + genero + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dvds.add(mapearDvd(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dvds;
    }

    public List<Dvd> buscarCombinado(String titulo, String director, String genero) {
        List<Dvd> dvds = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM dvds WHERE ");
        List<String> condiciones = new ArrayList<>();
        List<String> parametros = new ArrayList<>();

        if (!titulo.isEmpty()) {
            condiciones.add("titulo LIKE ?");
            parametros.add("%" + titulo + "%");
        }
        if (!director.isEmpty()) {
            condiciones.add("director LIKE ?");
            parametros.add("%" + director + "%");
        }
        if (!genero.isEmpty()) {
            condiciones.add("genero LIKE ?");
            parametros.add("%" + genero + "%");
        }

        if (condiciones.isEmpty()) {
            return obtenerTodosDvds();
        }

        sql.append(String.join(" AND ", condiciones));

        try (PreparedStatement ps = conexion.prepareStatement(sql.toString())) {
            for (int i = 0; i < parametros.size(); i++) {
                ps.setString(i + 1, parametros.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dvds.add(mapearDvd(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dvds;
    }

    public List<String> obtenerGenerosUnicos() {
        List<String> generos = new ArrayList<>();
        String sql = "SELECT DISTINCT genero FROM dvds";
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

    public void actualizarDvd(Dvd dvd) {
        String sql = "UPDATE dvds SET titulo = ?, director = ?, genero = ?, anio_publicacion = ?, duracion_minutos = ?, disponible = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, dvd.getTitulo());
            ps.setString(2, dvd.getDirector());
            ps.setString(3, dvd.getGenero());
            ps.setInt(4, dvd.getAnioPublicacion());
            ps.setInt(5, dvd.getDuracionMinutos());
            ps.setBoolean(6, dvd.isDisponible());
            ps.setInt(7, dvd.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarDvd(int id) {
        String sql = "DELETE FROM dvds WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dvd obtenerDvdPorId(int id) {
        String sql = "SELECT * FROM dvds WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapearDvd(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Dvd mapearDvd(ResultSet rs) throws SQLException {
        Dvd dvd = new Dvd();
        dvd.setId(rs.getInt("id"));
        dvd.setTitulo(rs.getString("titulo"));
        dvd.setDirector(rs.getString("director"));
        dvd.setGenero(rs.getString("genero"));
        dvd.setAnioPublicacion(rs.getInt("anio_publicacion"));
        dvd.setDuracionMinutos(rs.getInt("duracion_minutos"));
        dvd.setDisponible(rs.getBoolean("disponible"));
        return dvd;
    }
}