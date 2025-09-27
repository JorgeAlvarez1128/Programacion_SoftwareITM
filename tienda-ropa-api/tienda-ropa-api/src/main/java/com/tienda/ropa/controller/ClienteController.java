package com.tienda.ropa.controller;
import com.tienda.ropa.model.Cliente;
import com.tienda.ropa.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController @RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService service;
    public ClienteController(ClienteService s){ this.service = s; }
    @GetMapping public ResponseEntity<?> all(){ return ResponseEntity.ok(service.list()); }
    @GetMapping("/{id}") public ResponseEntity<?> one(@PathVariable int id){
        var r = service.get(id); return r==null? ResponseEntity.notFound().build() : ResponseEntity.ok(r);
    }
    @PostMapping public ResponseEntity<?> create(@RequestBody Cliente c){
        int id = service.create(c); return ResponseEntity.created(URI.create("/api/clientes/"+id)).build();
    }
    @PutMapping("/{id}") public ResponseEntity<?> update(@PathVariable int id, @RequestBody Cliente c){
        return service.update(id, c)? ResponseEntity.noContent().build(): ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}") public ResponseEntity<?> delete(@PathVariable int id){
        return service.delete(id)? ResponseEntity.noContent().build(): ResponseEntity.notFound().build();
    }
}
