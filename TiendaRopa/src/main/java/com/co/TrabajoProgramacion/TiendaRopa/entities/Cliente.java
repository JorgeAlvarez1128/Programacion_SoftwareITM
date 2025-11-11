package com.co.TrabajoProgramacion.TiendaRopa.entities;

import lombok.*;
import jakarta.persistence.*;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Clientes")
public class Cliente implements Comparable<Cliente>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ClienteId")
    private Integer idCliente;

    @Column(name = "Nombre", nullable = false)
    private String nombre;

    @Column(name = "Telefono")
    private String telefono;

    @Column(name = "Email", nullable = false, unique = true)
    private String email;
    @Override
    public int compareTo(Cliente o) {
        return this.nombre.compareTo(o.nombre);
    }
}
