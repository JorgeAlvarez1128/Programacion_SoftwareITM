package com.co.TrabajoProgramacion.TiendaRopa.controllers;

import com.co.TrabajoProgramacion.TiendaRopa.entities.Cliente;
import com.co.TrabajoProgramacion.TiendaRopa.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/api/clientes", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // --------------------- GETS ---------------------
    @GetMapping("/listar")
    public ResponseEntity<?> listarClientes() {
        List<Cliente> clientes = clienteService.getClientes();
        if (clientes == null || clientes.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/listarId")
    public ResponseEntity<?> listarPorId(
            @RequestParam(value = "clienteId", required = false) Integer clienteId,
            @RequestParam(value = "id", required = false) Integer idAlt) {
        Integer id = (clienteId != null) ? clienteId : idAlt;
        if (id == null) return ResponseEntity.badRequest().body("El parámetro 'clienteId' (o 'id') es obligatorio.");
        Cliente cliente = clienteService.getClienteById(id);
        if (cliente == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el cliente con id: " + id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/listarByNombre")
    public ResponseEntity<?> listarPorNombre(@RequestParam("nombre") String nombre) {
        List<Cliente> clientes = clienteService.getClientesByNombre(nombre);
        if (clientes == null || clientes.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron clientes con el nombre: " + nombre);
        return ResponseEntity.ok(clientes);
    }

    // --------------------- INSERTAR ---------------------

    @PostMapping(value = "/insertar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> insertarClienteJson(@RequestBody Cliente cliente) {
        if (cliente == null || cliente.getNombre() == null || cliente.getEmail() == null)
            return ResponseEntity.badRequest().body("Los campos 'nombre' y 'email' son obligatorios.");
        boolean ok = clienteService.insertarCliente(cliente);
        return ok ? ResponseEntity.status(HttpStatus.CREATED).body("Cliente agregado correctamente")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo agregar el cliente");
    }


    @PostMapping(value = "/insertar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> insertarClienteForm(Cliente cliente) {

        if (cliente == null || cliente.getNombre() == null || cliente.getEmail() == null)
            return ResponseEntity.badRequest().body("Los campos 'nombre' y 'email' son obligatorios.");
        boolean ok = clienteService.insertarCliente(cliente);
        return ok ? ResponseEntity.status(HttpStatus.CREATED).body("Cliente agregado correctamente")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo agregar el cliente");
    }

    // --------------------- ACTUALIZAR ---------------------

    @PutMapping(value = "/actualizar", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> actualizarClienteJson(@RequestBody Cliente cliente) {
        if (cliente == null || cliente.getIdCliente() == null)
            return ResponseEntity.badRequest().body("El campo 'idCliente' es obligatorio para actualizar.");
        boolean ok = clienteService.actualizarCliente(cliente);
        return ok ? ResponseEntity.ok("Cliente actualizado correctamente")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo actualizar el cliente (verifica que exista el id).");
    }


    @PutMapping(value = "/actualizar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> actualizarClienteForm(Cliente cliente) {
        if (cliente == null || cliente.getIdCliente() == null)
            return ResponseEntity.badRequest().body("El campo 'idCliente' es obligatorio para actualizar.");
        boolean ok = clienteService.actualizarCliente(cliente);
        return ok ? ResponseEntity.ok("Cliente actualizado correctamente")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo actualizar el cliente (verifica que exista el id).");
    }

    // --------------------- ELIMINAR ---------------------
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable("id") Integer id) {
        if (id == null) return ResponseEntity.badRequest().body("El id es obligatorio.");
        boolean ok = clienteService.eliminarCliente(id);
        return ok ? ResponseEntity.ok("Cliente eliminado correctamente")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo eliminar el cliente (verifica que exista el id).");
    }
}
