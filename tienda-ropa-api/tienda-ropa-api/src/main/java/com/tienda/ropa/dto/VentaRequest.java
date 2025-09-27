package com.tienda.ropa.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public record VentaRequest(
        @NotNull Integer clienteId,
        @NotNull List<Item> items
) {
    public record Item(
            @NotNull Integer productoId,
            @Min(1) int cantidad,
            @NotNull BigDecimal precioUnit
    ){}
}
