package com.nuevolooksr.stock.dao;

import com.nuevolooksr.stock.model.Producto;
import com.nuevolooksr.stock.utils.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public void agregarProducto(Producto producto) {
        String sql = "INSERT INTO productos (nombre, descripcion, precio, stock_deposito, stock_local, id_categoria) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStockDeposito());
            stmt.setInt(5, producto.getStockLocal());
            stmt.setInt(6, producto.getIdCategoria());
            stmt.executeUpdate();

            // Obtener el ID generado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    producto.setId(rs.getInt(1));
                }
            }

            System.out.println("✅ Producto agregado correctamente: " + producto.getNombre());
        } catch (SQLException e) {
            System.err.println("❌ Error al agregar producto: " + e.getMessage());
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
            System.err.println("❌ Error al obtener productos: " + e.getMessage());
        }
        return productos;
    }

    public int[] consultarStock(int idProducto) {
        String sql = "SELECT stock_deposito, stock_local FROM productos WHERE id_producto = ?";
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new int[]{rs.getInt("stock_deposito"), rs.getInt("stock_local")};
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al consultar stock: " + e.getMessage());
        }
        return new int[]{0, 0}; // En caso de error, devuelve 0 en ambos valores
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

            registrarMovimiento(idProducto, cantidad, haciaLocal ? "MOVIMIENTO_LOCAL" : "AJUSTE");
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar stock: " + e.getMessage());
        }
    }

    private void registrarMovimiento(int idProducto, int cantidad, String tipo) {
        String sql = "INSERT INTO movimientos (id_producto, cantidad, tipo) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            stmt.setInt(2, cantidad);
            stmt.setString(3, tipo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al registrar movimiento: " + e.getMessage());
        }
    }
}
