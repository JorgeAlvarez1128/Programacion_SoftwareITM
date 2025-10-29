package com.co.TrabajoProgramacion.TiendaRopa.repositories;

import org.springframework.stereotype.Service;

@Service
public class ClienteRepositoryHelper {

    public String listarClientes() {
        return "SELECT ClienteId, Nombre, Email, Telefono FROM Clientes";
    }

    public String getClienteById() {
        return "SELECT ClienteId, Nombre, Email, Telefono FROM Clientes WHERE ClienteId = ?";
    }

    public String getClientesByNombre(String nombre) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ClienteId, Nombre, Email, Telefono FROM Clientes WHERE Nombre LIKE '%");
        sb.append(nombre);
        sb.append("%'");
        return sb.toString();
    }

    public String insertarCliente() {
        return "INSERT INTO Clientes (Nombre, Email, Telefono) VALUES (?, ?, ?)";
    }

    public String actualizarCliente() {
        return "UPDATE Clientes SET Nombre = ?, Email = ?, Telefono = ? WHERE ClienteId = ?";
    }

    public String eliminarCliente() {
        return "DELETE FROM Clientes WHERE ClienteId = ?";
    }
}
