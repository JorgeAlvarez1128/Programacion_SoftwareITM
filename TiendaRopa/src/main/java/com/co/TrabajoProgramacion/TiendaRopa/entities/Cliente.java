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
    private String apellido;
    private Date fechaNacimiento;
    private String genero;
    private String cedula;
    private String celular;
    private String email;
    private int edad;
    private String direccion;

    @Override
    public int compareTo(Cliente o) {
        return Integer.compare(this.edad, o.edad);
    }
}
