package com.nuevolooksr.stock.dao;

import com.nuevolooksr.stock.model.Categoria;
import com.nuevolooksr.stock.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CategoriaDAO {
    private static final Logger LOGGER = Logger.getLogger(CategoriaDAO.class.getName());

    public void agregarCategoria(String nombreCategoria) {
        String sql = "INSERT INTO categorias (nombre_categoria) VALUES (?)";
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreCategoria);
            stmt.executeUpdate();
            System.out.println("✅ Categoría agregada correctamente.");
        } catch (SQLException e) {
            LOGGER.severe("❌ Error al agregar categoría: " + e.getMessage());
        }
    }

    public List<Categoria> obtenerCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias";
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categorias.add(new Categoria(rs.getInt("id_categoria"), rs.getString("nombre_categoria")));
            }
        } catch (SQLException e) {
            LOGGER.severe("❌ Error al obtener categorías: " + e.getMessage());
        }
        return categorias;
    }

    public void eliminarCategoria(int idCategoria) {
        String sql = "DELETE FROM categorias WHERE id_categoria = ?";
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCategoria);
            stmt.executeUpdate();
            System.out.println("✅ Categoría eliminada correctamente.");
        } catch (SQLException e) {
            LOGGER.severe("❌ Error al eliminar categoría: " + e.getMessage());
        }
    }

    public void actualizarCategoria(int idCategoria, String nuevoNombre) {
        String sql = "UPDATE categorias SET nombre_categoria = ? WHERE id_categoria = ?";
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoNombre);
            stmt.setInt(2, idCategoria);
            stmt.executeUpdate();
            System.out.println("✅ Categoría actualizada correctamente.");
        } catch (SQLException e) {
            LOGGER.severe("❌ Error al actualizar categoría: " + e.getMessage());
        }
    }
}
