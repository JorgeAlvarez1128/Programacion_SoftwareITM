package com.co.TrabajoProgramacion.TiendaRopa.repositories;

import org.springframework.stereotype.Service;

@Service
public class ProductoRepositoryHelper {

    public String listarProductos() {
        return "SELECT ProductoId, CodigoBarra, Nombre, CategoriaId, Talla, Color, ProveedorId, PrecioVenta FROM Productos";
    }

    public String getProductoById() {
        return "SELECT ProductoId, CodigoBarra, Nombre, CategoriaId, Talla, Color, ProveedorId, PrecioVenta FROM Productos WHERE ProductoId = ?";
    }

    // Nota: igual que el helper de Cliente de tu profe, se arma el LIKE aquí
    public String getProductosByNombre(String nombre) {
        return "SELECT ProductoId, CodigoBarra, Nombre, CategoriaId, Talla, Color, ProveedorId, PrecioVenta " +
                "FROM Productos WHERE Nombre LIKE '%" + nombre + "%' ORDER BY Nombre ASC";
    }

    public String insertarProducto() {
        return "INSERT INTO Productos (CodigoBarra, Nombre, CategoriaId, Talla, Color, ProveedorId, PrecioVenta) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    public String actualizarProducto() {
        return "UPDATE Productos SET CodigoBarra=?, Nombre=?, CategoriaId=?, Talla=?, Color=?, ProveedorId=?, PrecioVenta=? " +
                "WHERE ProductoId=?";
    }

    public String eliminarProducto() {
        return "DELETE FROM Productos WHERE ProductoId = ?";
    }
}
