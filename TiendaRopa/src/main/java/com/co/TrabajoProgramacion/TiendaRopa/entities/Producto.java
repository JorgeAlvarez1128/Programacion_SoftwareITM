package com.co.TrabajoProgramacion.TiendaRopa.entities;
import lombok.*;
import jakarta.persistence.*;
import java.util.Date;
@Entity
@Table(name = "Productos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductoId")
    private Integer productoId;

    @Column(name = "CodigoBarra")
    private String codigoBarra;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @Column(name = "CategoriaId")
    private Integer categoriaId;

    @Column(name = "Talla")
    private String talla;

    @Column(name = "Color")
    private String color;

    @Column(name = "ProveedorId")
    private Integer proveedorId;

    @Column(name = "PrecioVenta", nullable = false)
    private Double precioVenta;
}
