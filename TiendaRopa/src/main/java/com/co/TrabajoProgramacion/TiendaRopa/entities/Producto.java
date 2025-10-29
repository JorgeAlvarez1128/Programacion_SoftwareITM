package com.co.TrabajoProgramacion.TiendaRopa.entities;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto {
    private Integer idProducto;
    private String codigoBarra;
    private String nombre;
    private Integer categoriaId;
    private String talla;
    private String color;
    private Integer proveedorId;
    private Double precioVenta;

}
