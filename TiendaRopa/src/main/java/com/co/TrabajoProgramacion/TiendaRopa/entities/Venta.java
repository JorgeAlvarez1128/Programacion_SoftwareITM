package com.co.TrabajoProgramacion.TiendaRopa.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VentaId")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClienteId", nullable = false) // FK a Clientes.ClienteId
    private Cliente cliente;

    @Column(name = "FechaVenta", nullable = false)
    private LocalDateTime fechaVenta;

    @Column(name = "Total", nullable = false)
    private Double total;

    @OneToMany(mappedBy = "venta",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<VentaItem> items = new ArrayList<>();
}
