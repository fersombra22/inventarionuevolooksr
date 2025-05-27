package com.nuevolooksr.stock.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/nuevolooksr_db";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";
    private static Connection instancia;
    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());

    public static Connection conectar() throws SQLException {
        if (instancia == null || instancia.isClosed()) {
            instancia = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        }
        return instancia;
    }

    public static void cerrarConexion() {
        if (instancia != null) {
            try {
                instancia.close();
                instancia = null;
            } catch (SQLException e) {
                LOGGER.severe("❌ Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
