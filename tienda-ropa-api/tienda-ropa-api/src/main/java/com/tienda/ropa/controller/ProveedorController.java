package com.tienda.ropa.controller;
import com.tienda.ropa.model.Proveedor;
import com.tienda.ropa.service.ProveedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController @RequestMapping("/api/proveedores")
public class ProveedorController {
    private final ProveedorService service;
    public ProveedorController(ProveedorService s){ this.service = s; }
    @GetMapping public ResponseEntity<?> all(){ return ResponseEntity.ok(service.list()); }
    @GetMapping("/{id}") public ResponseEntity<?> one(@PathVariable int id){
        var r = service.get(id); return r==null? ResponseEntity.notFound().build() : ResponseEntity.ok(r);
    }
    @PostMapping public ResponseEntity<?> create(@RequestBody Proveedor p){
        int id = service.create(p); return ResponseEntity.created(URI.create("/api/proveedores/"+id)).build();
    }
    @PutMapping("/{id}") public ResponseEntity<?> update(@PathVariable int id, @RequestBody Proveedor p){
        return service.update(id, p)? ResponseEntity.noContent().build(): ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}") public ResponseEntity<?> delete(@PathVariable int id){
        return service.delete(id)? ResponseEntity.noContent().build(): ResponseEntity.notFound().build();
    }
}
