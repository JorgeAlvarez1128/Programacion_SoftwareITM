package com.co.TrabajoProgramacion.TiendaRopa.services;

import com.co.TrabajoProgramacion.TiendaRopa.entities.Producto;
import com.co.TrabajoProgramacion.TiendaRopa.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getProductos() {
        return productoRepository.getProductos();
    }

    public Producto getProductoById(int id) {
        return productoRepository.getProductoById(id);
    }

    public List<Producto> getProductosByNombre(String nombre) {
        return productoRepository.getProductosByNombre(nombre);
    }

    public boolean insertarProducto(Producto p) {
        return productoRepository.insertarProducto(p);
    }

    public boolean actualizarProducto(Producto p) {
        return productoRepository.actualizarProducto(p);
    }

    public boolean eliminarProducto(int id) {
        return productoRepository.eliminarProducto(id);
    }
}
