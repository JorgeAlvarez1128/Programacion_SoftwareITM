package com.co.TrabajoProgramacion.TiendaRopa.entities;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente implements Comparable<Cliente>{
    private Integer idCliente;
    private String nombre;
    private String celular;
    private String email;

    @Override
    public int compareTo(Cliente o) {
        return this.nombre.compareTo(o.nombre);
    }
}
