package com.co.TrabajoProgramacion.TiendaRopa.dto.venta;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record VentaCreateDTO(
        @NotNull Integer clienteId,
        @NotNull Integer productoId, // o prendaId
        @NotNull @Positive Integer cantidad
) {}