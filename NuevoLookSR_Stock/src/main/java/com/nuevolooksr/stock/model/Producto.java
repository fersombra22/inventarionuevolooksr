package com.nuevolooksr.stock.model;

import com.nuevolooksr.stock.dao.CategoriaDAO;

public class Producto {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stockDeposito;
    private int stockLocal;
    private int idCategoria; // Referencia a la tabla categorías

    public Producto(int id, String nombre, String descripcion, double precio, int stockDeposito, int stockLocal, int idCategoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stockDeposito = stockDeposito;
        this.stockLocal = stockLocal;
        this.idCategoria = idCategoria;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getStockDeposito() { return stockDeposito; }
    public void setStockDeposito(int stockDeposito) { this.stockDeposito = stockDeposito; }
    public int getStockLocal() { return stockLocal; }
    public void setStockLocal(int stockLocal) { this.stockLocal = stockLocal; }
    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public String getNombreCategoria() {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        return categoriaDAO.obtenerCategorias().stream()
                .filter(c -> c.getId() == this.idCategoria)
                .map(Categoria::getNombre)
                .findFirst()
                .orElse("Sin categoría");
    }
}
