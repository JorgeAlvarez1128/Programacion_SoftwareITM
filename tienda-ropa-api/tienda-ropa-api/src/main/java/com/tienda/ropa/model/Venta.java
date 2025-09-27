package com.tienda.ropa.model;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
public class Venta {
    private Integer ventaId;
    private Integer clienteId;
    private LocalDateTime fechaVenta;
    private BigDecimal total;
}
