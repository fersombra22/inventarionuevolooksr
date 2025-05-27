package com.nuevolooksr.stock.controllers;

import com.nuevolooksr.stock.utils.PasswordUtil;
import com.nuevolooksr.stock.utils.StageManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/nuevolooksr_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public void initialize() {
        loginButton.setOnAction(event -> login());
    }

    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Debe completar todos los campos.");
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT password FROM usuarios WHERE username = ?")) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (PasswordUtil.verifyPassword(password, storedPassword)) {
                    showAlert("Éxito", "Login correcto. Bienvenido, " + username + "!");
                    StageManager.changeScene("/com/nuevolooksr/stock/views/MenuView.fxml");
                } else {
                    showAlert("Error", "Contraseña incorrecta.");
                }
            } else {
                showAlert("Error", "Usuario no encontrado.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Problema con la base de datos: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
