package com.tienda.ropa.model;
import lombok.*;
import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor
public class VentaItem {
    private Integer ventaItemId;
    private Integer ventaId;
    private Integer productoId;
    private Integer cantidad;
    private BigDecimal precioUnit;
    private BigDecimal subtotal;
}
