package com.tienda.ropa.model;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class Cliente {
    private Integer clienteId;
    private String nombre;
    private String email;
    private String telefono;
}
