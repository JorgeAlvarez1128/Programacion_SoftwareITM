package com.tienda.ropa.controller;
import com.tienda.ropa.model.Inventario;
import com.tienda.ropa.service.InventarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController @RequestMapping("/api/inventario")
public class InventarioController {
    private final InventarioService service;
    public InventarioController(InventarioService s){ this.service = s; }
    @GetMapping public ResponseEntity<?> all(){ return ResponseEntity.ok(service.list()); }
    @GetMapping("/{id}") public ResponseEntity<?> one(@PathVariable int id){
        var r = service.get(id); return r==null? ResponseEntity.notFound().build() : ResponseEntity.ok(r);
    }
    @PostMapping public ResponseEntity<?> create(@RequestBody Inventario i){
        int id = service.create(i); return ResponseEntity.created(URI.create("/api/inventario/"+id)).build();
    }
    @PutMapping("/{id}") public ResponseEntity<?> update(@PathVariable int id, @RequestBody Inventario i){
        return service.update(id, i)? ResponseEntity.noContent().build(): ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}") public ResponseEntity<?> delete(@PathVariable int id){
        return service.delete(id)? ResponseEntity.noContent().build(): ResponseEntity.notFound().build();
    }
}
