package com.tienda.ropa.model;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class Inventario {
    private Integer inventarioId;
    private Integer productoId;
    private Integer cantidad;
    private String ubicacion;
}
