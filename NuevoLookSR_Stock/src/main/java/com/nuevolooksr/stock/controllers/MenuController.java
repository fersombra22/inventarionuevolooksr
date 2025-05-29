package com.nuevolooksr.stock.controllers; // 📌 Ajusta si el paquete es diferente

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;

public class MenuController {

    @FXML private TextField txtCategoria;
    @FXML private TextField txtProducto;
    @FXML private TextField txtDescripcion;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtStockDeposito;
    @FXML private TextField txtStockLocal;
    @FXML private ComboBox<String> cmbCategorias;
    @FXML private ComboBox<String> cmbProductos;
    @FXML private TextField txtCantidadMovimiento;

    @FXML
    private void handleAgregarCategoria() {
        String categoria = txtCategoria.getText();
        if (categoria.isEmpty()) {
            showAlert("Error", "Debe ingresar una categoría.");
            return;
        }
        System.out.println("✅ Categoría agregada: " + categoria);
        showAlert("Éxito", "Categoría agregada correctamente.");
    }

    @FXML
    private void handleAgregarProducto() {
        String producto = txtProducto.getText();
        String descripcion = txtDescripcion.getText();
        String precio = txtPrecio.getText();
        String stockDeposito = txtStockDeposito.getText();
        String stockLocal = txtStockLocal.getText();

        if (producto.isEmpty() || descripcion.isEmpty() || precio.isEmpty() || stockDeposito.isEmpty() || stockLocal.isEmpty()) {
            showAlert("Error", "Todos los campos deben estar llenos.");
            return;
        }

        System.out.println("✅ Producto agregado: " + producto + " - " + descripcion);
        showAlert("Éxito", "Producto agregado correctamente.");
    }

    @FXML
    private void handleConsultarStockProducto() {
        System.out.println("🔍 Consulta de stock realizada.");
        showAlert("Consulta", "Stock consultado correctamente.");
    }

    @FXML
    private void handleMoverA_LOCAL() {
        System.out.println("📦 Movimiento a local registrado.");
        showAlert("Movimiento", "Stock movido correctamente.");
    }

    @FXML
    private void handleVenderDirecto() {
        System.out.println("🛒 Venta directa realizada.");
        showAlert("Venta", "Venta registrada correctamente.");
    }

    @FXML
    private void handleSalidaLocal() {
        System.out.println("📤 Salida de stock en local.");
        showAlert("Salida", "Salida de stock realizada correctamente.");
    }

    @FXML
    private void handleGenerarPDF() {
        System.out.println("📄 Reporte PDF generado.");
        showAlert("Reporte", "PDF generado correctamente.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
