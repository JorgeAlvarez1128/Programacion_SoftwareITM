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
    public ProductoController(ProductoService productoService) { this.productoService = productoService; }

    // -------- GETS --------
    @GetMapping("/listar")
    public ResponseEntity<?> listarProductos() {
        List<Producto> productos = productoService.getProductos();
        if (productos == null || productos.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/listarId")
    public ResponseEntity<?> listarPorId(@RequestParam(value = "productoId", required = false) Integer productoId,
                                         @RequestParam(value = "id", required = false) Integer idAlt) {
        Integer id = (productoId != null) ? productoId : idAlt;
        if (id == null) return ResponseEntity.badRequest().body("El parámetro 'productoId' (o 'id') es obligatorio.");
        Producto p = productoService.getProductoById(id);
        if (p == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el producto con id: " + id);
        return ResponseEntity.ok(p);
    }

    @GetMapping("/listarByNombre")
    public ResponseEntity<?> listarPorNombre(@RequestParam("nombre") String nombre) {
        List<Producto> productos = productoService.getProductosByNombre(nombre);
        if (productos == null || productos.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron productos con el nombre: " + nombre);
        return ResponseEntity.ok(productos);
    }

    // -------- INSERTAR --------
    // JSON
    @PostMapping(value = "/insertar", consumes = { MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8" })
    public ResponseEntity<?> insertarJson(@RequestBody Producto p) {
        if (p == null || p.getNombre() == null || p.getPrecioVenta() == null)
            return ResponseEntity.badRequest().body("Los campos 'nombre' y 'precioVenta' son obligatorios.");
        boolean ok = productoService.insertarProducto(p);
        return ok ? ResponseEntity.status(HttpStatus.CREATED).body("Producto agregado correctamente")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo agregar el producto");
    }

    // FORM
    @PostMapping(value = "/insertar", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> insertarForm(Producto p) {
        if (p == null || p.getNombre() == null || p.getPrecioVenta() == null)
            return ResponseEntity.badRequest().body("Los campos 'nombre' y 'precioVenta' son obligatorios.");
        boolean ok = productoService.insertarProducto(p);
        return ok ? ResponseEntity.status(HttpStatus.CREATED).body("Producto agregado correctamente")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo agregar el producto");
    }

    // -------- ACTUALIZAR --------
    // JSON
    @PutMapping(value = "/actualizar", consumes = { MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8" })
    public ResponseEntity<?> actualizarJson(@RequestBody Producto p) {
        if (p == null || p.getIdProducto() == null)
            return ResponseEntity.badRequest().body("El campo 'idProducto' es obligatorio para actualizar.");
        boolean ok = productoService.actualizarProducto(p);
        return ok ? ResponseEntity.ok("Producto actualizado correctamente")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo actualizar el producto (verifica que exista el id).");
    }

    // FORM
    @PutMapping(value = "/actualizar", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> actualizarForm(Producto p) {
        if (p == null || p.getIdProducto() == null)
            return ResponseEntity.badRequest().body("El campo 'idProducto' es obligatorio para actualizar.");
        boolean ok = productoService.actualizarProducto(p);
        return ok ? ResponseEntity.ok("Producto actualizado correctamente")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo actualizar el producto (verifica que exista el id).");
    }

    // -------- ELIMINAR --------
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) {
        if (id == null) return ResponseEntity.badRequest().body("El id es obligatorio.");
        boolean ok = productoService.eliminarProducto(id);
        return ok ? ResponseEntity.ok("Producto eliminado correctamente")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo eliminar el producto (verifica que no tenga dependencias).");
    }
}
