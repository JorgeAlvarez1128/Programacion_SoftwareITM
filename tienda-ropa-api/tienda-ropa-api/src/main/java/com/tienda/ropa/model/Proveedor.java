package com.tienda.ropa.model;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class Proveedor {
    private Integer proveedorId;
    private String nombre;
    private String contacto;
    private String telefono;
}
