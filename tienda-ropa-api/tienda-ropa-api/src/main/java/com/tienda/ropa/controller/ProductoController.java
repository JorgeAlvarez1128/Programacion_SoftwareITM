package com.tienda.ropa.controller;
import com.tienda.ropa.model.Producto;
import com.tienda.ropa.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController @RequestMapping("/api/productos")
public class ProductoController {
    private final ProductoService service;
    public ProductoController(ProductoService s){ this.service = s; }

    @GetMapping public ResponseEntity<?> all(){ return ResponseEntity.ok(service.list()); }
    @GetMapping("/{id}") public ResponseEntity<?> one(@PathVariable int id){
        var r = service.get(id); return r==null? ResponseEntity.notFound().build() : ResponseEntity.ok(r);
    }
    @GetMapping("/categoria/{categoriaId}") public ResponseEntity<?> byCategoria(@PathVariable int categoriaId){
        return ResponseEntity.ok(service.byCategoria(categoriaId));
    }
    @PostMapping public ResponseEntity<?> create(@RequestBody Producto p){
        int id = service.create(p); return ResponseEntity.created(URI.create("/api/productos/"+id)).build();
    }
    @PutMapping("/{id}") public ResponseEntity<?> update(@PathVariable int id, @RequestBody Producto p){
        return service.update(id, p)? ResponseEntity.noContent().build(): ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}") public ResponseEntity<?> delete(@PathVariable int id){
        return service.delete(id)? ResponseEntity.noContent().build(): ResponseEntity.notFound().build();
    }
}
