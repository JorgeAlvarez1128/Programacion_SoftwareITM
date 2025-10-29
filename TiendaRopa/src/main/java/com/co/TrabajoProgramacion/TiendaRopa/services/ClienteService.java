package com.co.TrabajoProgramacion.TiendaRopa.services;

import com.co.TrabajoProgramacion.TiendaRopa.entities.Cliente;
import com.co.TrabajoProgramacion.TiendaRopa.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
//CRUD basico
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository; // Implementa ClienteRepositoryInterface con JDBC

    public List<Cliente> getClientes() {
        return clienteRepository.getClientes();
    }

    public Cliente getClienteById(int id) {
        return clienteRepository.getClienteById(id);
    }

    public List<Cliente> getClientesByNombre(String nombre) {
        return clienteRepository.getClientesByNombre(nombre);
    }

    public boolean insertarCliente(Cliente cliente) {
        return clienteRepository.insertarCliente(cliente);
    }

    public boolean actualizarCliente(Cliente cliente) {
        return clienteRepository.actualizarCliente(cliente);
    }

    public boolean eliminarCliente(int id) {
        return clienteRepository.eliminarCliente(id);
    }
}
