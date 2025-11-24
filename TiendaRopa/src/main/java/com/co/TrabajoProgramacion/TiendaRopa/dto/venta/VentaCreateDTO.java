package com.co.TrabajoProgramacion.TiendaRopa.dto.venta;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record VentaCreateDTO(
        @NotNull Integer clienteId,
        @NotNull Integer productoId,
        @NotNull @Positive Integer cantidad,
        LocalDate fechaVenta
) {}
