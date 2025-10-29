package com.co.TrabajoProgramacion.TiendaRopa.repositories;

import com.co.TrabajoProgramacion.TiendaRopa.entities.Cliente;
import java.util.List;

public interface ClienteRepositoryInterface {
    List<Cliente> getClientes();
    Cliente getClienteById(int id);
    List<Cliente> getClientesByNombre(String nombre);
    boolean insertarCliente(Cliente cliente);
    boolean actualizarCliente(Cliente cliente);
    boolean eliminarCliente(int id);
}
