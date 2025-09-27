package com.tienda.ropa.controller;

import com.tienda.ropa.model.Rol;
import com.tienda.ropa.service.RolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/api/roles")
public class RolController {
    private final RolService service;
    public RolController(RolService s){ this.service = s; }

    @GetMapping public ResponseEntity<?> all(){ return ResponseEntity.ok(service.list()); }
    @GetMapping("/{id}") public ResponseEntity<?> one(@PathVariable int id){
        var r = service.get(id); return r==null? ResponseEntity.notFound().build() : ResponseEntity.ok(r);
    }
    @PostMapping public ResponseEntity<?> create(@RequestBody Rol r){
        int id = service.create(r); return ResponseEntity.created(URI.create("/api/roles/"+id)).build();
    }
    @PutMapping("/{id}") public ResponseEntity<?> update(@PathVariable int id, @RequestBody Rol r){
        return service.update(id, r)? ResponseEntity.noContent().build(): ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}") public ResponseEntity<?> delete(@PathVariable int id){
        return service.delete(id)? ResponseEntity.noContent().build(): ResponseEntity.notFound().build();
    }
}
