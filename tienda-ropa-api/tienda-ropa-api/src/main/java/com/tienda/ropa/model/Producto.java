package com.tienda.ropa.model;
import lombok.*;
import java.math.BigDecimal;

@Data @NoArgsConstructor @AllArgsConstructor
public class Producto {
    private Integer productoId;
    private String codigoBarra;
    private String nombre;
    private Integer categoriaId;
    private String talla;
    private String color;
    private Integer proveedorId;
    private BigDecimal precioVenta;
}
