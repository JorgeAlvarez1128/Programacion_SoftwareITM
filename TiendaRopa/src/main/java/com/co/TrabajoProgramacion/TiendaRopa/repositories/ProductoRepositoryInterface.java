package com.co.TrabajoProgramacion.TiendaRopa.repositories;

import com.co.TrabajoProgramacion.TiendaRopa.entities.Producto;
import java.util.List;

public interface ProductoRepositoryInterface {
    List<Producto> getProductos();
    Producto getProductoById(int idProducto);
    List<Producto> getProductosByNombre(String nombre);
    boolean insertarProducto(Producto p);
    boolean actualizarProducto(Producto p);
    boolean eliminarProducto(int idProducto);
}
