package com.biblioteca.dao;

import com.biblioteca.model.Revista;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RevistaDAO {
    private Connection conexion;

    public RevistaDAO() {
        conexion = ConexionBD.getConexion();
    }

    public void agregarRevista(Revista revista) {
        String sql = "INSERT INTO revistas (titulo, editor, genero, anio_publicacion, numero, paginas) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, revista.getTitulo());
            ps.setString(2, revista.getEditor());
            ps.setString(3, revista.getGenero());
            ps.setInt(4, revista.getAnioPublicacion());
            ps.setInt(5, revista.getNumero());
            ps.setInt(6, revista.getPaginas());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                revista.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Revista> obtenerTodasRevistas() {
        List<Revista> revistas = new ArrayList<>();
        String sql = "SELECT * FROM revistas";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                revistas.add(mapearRevista(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revistas;
    }

    public List<Revista> buscarPorTitulo(String titulo) {
        List<Revista> revistas = new ArrayList<>();
        String sql = "SELECT * FROM revistas WHERE titulo LIKE ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, "%" + titulo + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                revistas.add(mapearRevista(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revistas;
    }

    public List<Revista> buscarPorEditor(String editor) {
        List<Revista> revistas = new ArrayList<>();
        String sql = "SELECT * FROM revistas WHERE editor LIKE ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, "%" + editor + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                revistas.add(mapearRevista(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revistas;
    }

    public List<Revista> buscarPorGenero(String genero) {
        List<Revista> revistas = new ArrayList<>();
        String sql = "SELECT * FROM revistas WHERE genero LIKE ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, "%" + genero + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                revistas.add(mapearRevista(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revistas;
    }

    public List<Revista> buscarCombinado(String titulo, String editor, String genero) {
        List<Revista> revistas = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM revistas WHERE ");
        List<String> condiciones = new ArrayList<>();
        List<String> parametros = new ArrayList<>();

        if (!titulo.isEmpty()) {
            condiciones.add("titulo LIKE ?");
            parametros.add("%" + titulo + "%");
        }
        if (!editor.isEmpty()) {
            condiciones.add("editor LIKE ?");
            parametros.add("%" + editor + "%");
        }
        if (!genero.isEmpty()) {
            condiciones.add("genero LIKE ?");
            parametros.add("%" + genero + "%");
        }

        if (condiciones.isEmpty()) {
            return obtenerTodasRevistas();
        }

        sql.append(String.join(" AND ", condiciones));

        try (PreparedStatement ps = conexion.prepareStatement(sql.toString())) {
            for (int i = 0; i < parametros.size(); i++) {
                ps.setString(i + 1, parametros.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                revistas.add(mapearRevista(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revistas;
    }

    public List<String> obtenerGenerosUnicos() {
        List<String> generos = new ArrayList<>();
        String sql = "SELECT DISTINCT genero FROM revistas";
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

    public void actualizarRevista(Revista revista) {
        String sql = "UPDATE revistas SET titulo = ?, editor = ?, genero = ?, anio_publicacion = ?, numero = ?, paginas = ?, disponible = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, revista.getTitulo());
            ps.setString(2, revista.getEditor());
            ps.setString(3, revista.getGenero());
            ps.setInt(4, revista.getAnioPublicacion());
            ps.setInt(5, revista.getNumero());
            ps.setInt(6, revista.getPaginas());
            ps.setBoolean(7, revista.isDisponible());
            ps.setInt(8, revista.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarRevista(int id) {
        String sql = "DELETE FROM revistas WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Revista obtenerRevistaPorId(int id) {
        String sql = "SELECT * FROM revistas WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapearRevista(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Revista mapearRevista(ResultSet rs) throws SQLException {
        Revista revista = new Revista();
        revista.setId(rs.getInt("id"));
        revista.setTitulo(rs.getString("titulo"));
        revista.setEditor(rs.getString("editor"));
        revista.setGenero(rs.getString("genero"));
        revista.setAnioPublicacion(rs.getInt("anio_publicacion"));
        revista.setNumero(rs.getInt("numero"));
        revista.setPaginas(rs.getInt("paginas"));
        revista.setDisponible(rs.getBoolean("disponible"));
        return revista;
    }
}