package com.co.TrabajoProgramacion.TiendaRopa.controllers;

import com.co.TrabajoProgramacion.TiendaRopa.entities.Producto;
import com.co.TrabajoProgramacion.TiendaRopa.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // ----------- GET ALL -----------
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = productoService.getProductos();
        if (productos == null || productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    // ----------- GET BY ID -----------
    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") Integer id) {
        if (id == null)
            return ResponseEntity.badRequest().body("El parámetro 'id' es obligatorio.");
        Producto p = productoService.getProductoById(id);
        if (p == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró el producto con id: " + id);
        return ResponseEntity.ok(p);
    }

    // ----------- GET BY NAME -----------
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> listarPorNombre(@PathVariable("nombre") String nombre) {
        List<Producto> productos = productoService.getProductosByNombre(nombre);
        if (productos == null || productos.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontraron productos con el nombre: " + nombre);
        return ResponseEntity.ok(productos);
    }

    // ----------- CREATE -----------
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertarProducto(@RequestBody Producto p) {
        if (p == null || p.getNombre() == null || p.getPrecioVenta() == null)
            return ResponseEntity.badRequest()
                    .body("Los campos 'nombre' y 'precioVenta' son obligatorios.");

        boolean ok = productoService.insertarProducto(p);
        return ok
                ? ResponseEntity.status(HttpStatus.CREATED).body(p)
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("No se pudo agregar el producto.");
    }

    // ----------- UPDATE -----------
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarProducto(@PathVariable("id") Integer id, @RequestBody Producto p) {
        if (p == null)
            return ResponseEntity.badRequest().body("El producto no puede ser nulo.");
        p.setProductoId(id);
        boolean ok = productoService.actualizarProducto(p);
        return ok
                ? ResponseEntity.ok(p)
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se pudo actualizar el producto (verifica que exista el id).");
    }

    // ----------- DELETE -----------
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) {
        if (id == null)
            return ResponseEntity.badRequest().body("El id es obligatorio.");
        boolean ok = productoService.eliminarProducto(id);
        return ok
                ? ResponseEntity.ok("Producto eliminado correctamente.")
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No se pudo eliminar el producto (verifica que no tenga dependencias).");
    }
}
