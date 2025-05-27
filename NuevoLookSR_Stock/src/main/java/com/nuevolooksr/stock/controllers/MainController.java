package com.nuevolooksr.stock.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {
    @FXML private Button openProductos;
    @FXML private Button openCategorias;

    public void initialize() {
        System.out.println("MainController inicializado correctamente.");
    }

    @FXML
    private void handleOpenProductos() {
        System.out.println("Gestión de Productos abierta.");
        // Aquí puedes cambiar la vista o abrir una nueva ventana.
    }

    @FXML
    private void handleOpenCategorias() {
        System.out.println("Gestión de Categorías abierta.");
        // Aquí puedes cambiar la vista o abrir una nueva ventana.
    }
}
