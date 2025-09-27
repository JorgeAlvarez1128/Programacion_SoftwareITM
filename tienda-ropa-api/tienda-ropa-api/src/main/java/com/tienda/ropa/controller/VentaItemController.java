package com.tienda.ropa.controller;
import com.tienda.ropa.model.VentaItem;
import com.tienda.ropa.service.VentaItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController @RequestMapping("/api/venta-items")
public class VentaItemController {
    private final VentaItemService service;
    public VentaItemController(VentaItemService s){ this.service = s; }
    @GetMapping public ResponseEntity<?> all(){ return ResponseEntity.ok(service.list()); }
    @GetMapping("/{id}") public ResponseEntity<?> one(@PathVariable int id){
        var r = service.get(id); return r==null? ResponseEntity.notFound().build() : ResponseEntity.ok(r);
    }
    @GetMapping("/venta/{ventaId}") public ResponseEntity<?> byVenta(@PathVariable int ventaId){
        return ResponseEntity.ok(service.byVenta(ventaId));
    }
    @PostMapping public ResponseEntity<?> create(@RequestBody VentaItem vi){
        int id = service.create(vi); return ResponseEntity.created(URI.create("/api/venta-items/"+id)).build();
    }
    @PutMapping("/{id}") public ResponseEntity<?> update(@PathVariable int id, @RequestBody VentaItem vi){
        return service.update(id, vi)? ResponseEntity.noContent().build(): ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}") public ResponseEntity<?> delete(@PathVariable int id){
        return service.delete(id)? ResponseEntity.noContent().build(): ResponseEntity.notFound().build();
    }
}
