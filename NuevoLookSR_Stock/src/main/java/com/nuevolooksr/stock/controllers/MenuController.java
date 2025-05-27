package com.nuevolooksr.stock.controllers;

import com.nuevolooksr.stock.dao.CategoriaDAO;
import com.nuevolooksr.stock.dao.ProductoDAO;
import com.nuevolooksr.stock.model.Categoria;
import com.nuevolooksr.stock.model.Producto;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.util.List;

public class MenuController {
    @FXML private TextField txtCategoria, txtProducto, txtDescripcion, txtPrecio, txtStockDeposito, txtStockLocal, txtCantidadMovimiento;
    @FXML private ComboBox<String> cmbCategorias, cmbProductos;
    @FXML private Button btnAgregarCategoria, btnAgregarProducto, btnMoverA_LOCAL, btnVenderDirecto;

    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final ProductoDAO productoDAO = new ProductoDAO();

    @FXML
    public void initialize() {
        cargarCategorias();
        cargarProductos();
        btnAgregarCategoria.setOnAction(event -> handleAgregarCategoria());
        btnAgregarProducto.setOnAction(event -> handleAgregarProducto());
        btnMoverA_LOCAL.setOnAction(event -> handleMoverA_LOCAL());
        btnVenderDirecto.setOnAction(event -> handleVenderDirecto());
    }

    private void cargarCategorias() {
        List<Categoria> listaCategorias = categoriaDAO.obtenerCategorias();
        cmbCategorias.getItems().clear();
        for (Categoria categoria : listaCategorias) {
            cmbCategorias.getItems().add(categoria.getNombre());
        }
    }

    private void cargarProductos() {
        List<Producto> listaProductos = productoDAO.obtenerProductos();
        cmbProductos.getItems().clear();
        for (Producto producto : listaProductos) {
            cmbProductos.getItems().add(producto.getNombre());
        }
    }

    @FXML
    private void handleAgregarCategoria() {
        String categoria = txtCategoria.getText();
        if (categoria.isEmpty()) {
            showAlert("Error", "Debe ingresar un nombre de categoría.");
            return;
        }

        categoriaDAO.agregarCategoria(categoria);
        showAlert("Éxito", "Categoría agregada correctamente.");
        txtCategoria.clear();
        cargarCategorias();
    }

    @FXML
    private void handleAgregarProducto() {
        String producto = txtProducto.getText();
        String descripcion = txtDescripcion.getText();
        String categoriaSeleccionada = cmbCategorias.getSelectionModel().getSelectedItem();
        double precio;
        int stockDeposito, stockLocal;

        if (producto.isEmpty() || descripcion.isEmpty() || categoriaSeleccionada == null) {
            showAlert("Error", "Debe ingresar nombre, descripción y seleccionar una categoría.");
            return;
        }

        try {
            precio = Double.parseDouble(txtPrecio.getText());
            stockDeposito = Integer.parseInt(txtStockDeposito.getText());
            stockLocal = Integer.parseInt(txtStockLocal.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Precio y stock deben ser valores numéricos.");
            return;
        }

        int idCategoria = categoriaDAO.obtenerCategorias().stream()
                .filter(c -> c.getNombre().equals(categoriaSeleccionada))
                .map(Categoria::getId)
                .findFirst()
                .orElse(-1);

        if (idCategoria == -1) {
            showAlert("Error", "Error al obtener la categoría.");
            return;
        }

        Producto nuevoProducto = new Producto(0, producto, descripcion, precio, stockDeposito, stockLocal, idCategoria);
        productoDAO.agregarProducto(nuevoProducto);

        showAlert("Éxito", "Producto agregado correctamente.");
        txtProducto.clear();
        txtDescripcion.clear();
        txtPrecio.clear();
        txtStockDeposito.clear();
        txtStockLocal.clear();
        cargarProductos();
    }

    @FXML
    private void handleMoverA_LOCAL() {
        String productoSeleccionado = cmbProductos.getSelectionModel().getSelectedItem();
        int cantidad;

        if (productoSeleccionado == null || txtCantidadMovimiento.getText().isEmpty()) {
            showAlert("Error", "Debe seleccionar un producto y una cantidad.");
            return;
        }

        try {
            cantidad = Integer.parseInt(txtCantidadMovimiento.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Cantidad debe ser un número.");
            return;
        }

        int idProducto = productoDAO.obtenerProductos().stream()
                .filter(p -> p.getNombre().equals(productoSeleccionado))
                .map(Producto::getId)
                .findFirst()
                .orElse(-1);

        if (idProducto == -1) {
            showAlert("Error", "Producto no encontrado.");
            return;
        }

        productoDAO.actualizarStock(idProducto, cantidad, true);
        showAlert("Éxito", "Stock movido al local correctamente.");
        cargarProductos();
    }

    @FXML
    private void handleVenderDirecto() {
        String productoSeleccionado = cmbProductos.getSelectionModel().getSelectedItem();
        int cantidad;

        if (productoSeleccionado == null || txtCantidadMovimiento.getText().isEmpty()) {
            showAlert("Error", "Debe seleccionar un producto y una cantidad.");
            return;
        }

        try {
            cantidad = Integer.parseInt(txtCantidadMovimiento.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Cantidad debe ser un número.");
            return;
        }

        int idProducto = productoDAO.obtenerProductos().stream()
                .filter(p -> p.getNombre().equals(productoSeleccionado))
                .map(Producto::getId)
                .findFirst()
                .orElse(-1);

        if (idProducto == -1) {
            showAlert("Error", "Producto no encontrado.");
            return;
        }

        productoDAO.actualizarStock(idProducto, cantidad, false);
        showAlert("Éxito", "Venta realizada correctamente.");
        cargarProductos();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
