package com.co.TrabajoProgramacion.TiendaRopa.entities;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "VentaItems") // coincide con tu BD
public class VentaItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VentaItemId")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "VentaId", nullable = false)
    private Venta venta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductoId", nullable = false)
    private Producto producto;

    @Column(name = "Cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "PrecioUnit", nullable = false)
    private Double precioUnit;

    // Columna calculada en BD; la mapeamos solo lectura (opcional)
    @Column(name = "Subtotal", insertable = false, updatable = false)
    private Double subtotal;
}
