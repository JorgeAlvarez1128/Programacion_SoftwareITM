package com.tienda.ropa.controller;

import com.tienda.ropa.dto.VentaRequest;
import com.tienda.ropa.model.Venta;
import com.tienda.ropa.service.VentaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController @RequestMapping("/api/ventas")
public class VentaController {
    private final VentaService service;
    public VentaController(VentaService s){ this.service = s; }

    @GetMapping public ResponseEntity<?> all(){ return ResponseEntity.ok(service.list()); }
    @GetMapping("/{id}") public ResponseEntity<?> one(@PathVariable int id){
        var r = service.get(id); return r==null? ResponseEntity.notFound().build() : ResponseEntity.ok(r);
    }
    @PostMapping("/crear")
    public ResponseEntity<?> crearVenta(@Valid @RequestBody VentaRequest req){
        int id = service.crearVenta(req);
        return ResponseEntity.created(URI.create("/api/ventas/"+id)).build();
    }
    @PutMapping("/{id}") public ResponseEntity<?> update(@PathVariable int id, @RequestBody Venta v){
        return service.update(id, v)? ResponseEntity.noContent().build(): ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}") public ResponseEntity<?> delete(@PathVariable int id){
        return service.delete(id)? ResponseEntity.noContent().build(): ResponseEntity.notFound().build();
    }
}
