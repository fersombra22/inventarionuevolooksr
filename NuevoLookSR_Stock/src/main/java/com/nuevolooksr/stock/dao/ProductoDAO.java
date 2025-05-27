package com.nuevolooksr.stock.dao;

import com.nuevolooksr.stock.model.Producto;
import com.nuevolooksr.stock.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProductoDAO {
    private static final Logger LOGGER = Logger.getLogger(ProductoDAO.class.getName());

    public void agregarProducto(Producto producto) {
        String sql = "INSERT INTO productos (nombre, descripcion, precio, stock_deposito, stock_local, id_categoria) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStockDeposito());
            stmt.setInt(5, producto.getStockLocal());
            stmt.setInt(6, producto.getIdCategoria());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe("❌ Error al agregar producto: " + e.getMessage());
        }
    }

    public List<Producto> obtenerProductos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                productos.add(new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getInt("stock_deposito"),
                        rs.getInt("stock_local"),
                        rs.getInt("id_categoria")
                ));
            }
        } catch (SQLException e) {
            LOGGER.severe("❌ Error al obtener productos: " + e.getMessage());
        }
        return productos;
    }

    public void actualizarStock(int idProducto, int cantidad, boolean haciaLocal) {
        String sql = haciaLocal
                ? "UPDATE productos SET stock_deposito = stock_deposito - ?, stock_local = stock_local + ? WHERE id_producto = ?"
                : "UPDATE productos SET stock_deposito = stock_deposito - ? WHERE id_producto = ?";

        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cantidad);
            if (haciaLocal) stmt.setInt(2, cantidad);
            stmt.setInt(haciaLocal ? 3 : 2, idProducto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.severe("❌ Error al actualizar stock: " + e.getMessage());
        }
    }
}
