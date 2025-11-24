package com.co.TrabajoProgramacion.TiendaRopa.controllers;

import com.co.TrabajoProgramacion.TiendaRopa.dto.venta.VentaCreateDTO;
import com.co.TrabajoProgramacion.TiendaRopa.services.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Ventas", description = "Operaciones de venta (Cliente + Producto)")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @Operation(summary = "Registrar una venta")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Venta registrada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping(value = "/crear", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> crear(@Valid @RequestBody VentaCreateDTO dto) {
        var res = ventaService.crearVenta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

    @Operation(summary = "Listar ventas por cliente")
    @GetMapping("/porCliente")
    public ResponseEntity<?> porCliente(@RequestParam Integer clienteId) {
        return ResponseEntity.ok(ventaService.listarPorCliente(clienteId));
    }
}
