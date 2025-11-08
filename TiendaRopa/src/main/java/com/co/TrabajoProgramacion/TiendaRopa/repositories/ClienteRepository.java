package com.co.TrabajoProgramacion.TiendaRopa.repositories;

import com.co.TrabajoProgramacion.TiendaRopa.entities.Cliente;
import com.co.TrabajoProgramacion.TiendaRopa.utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteRepository implements ClienteRepositoryInterface {

    @Autowired
    private ClienteRepositoryHelper clienteRepositoryHelper;

    @Override
    public List<Cliente> getClientes() {
        List<Cliente> clientes = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();

        try {
            PreparedStatement stmt = connection.prepareStatement(clienteRepositoryHelper.listarClientes());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("ClienteId"));
                cliente.setNombre(rs.getString("Nombre"));
                cliente.setEmail(rs.getString("Email"));
                cliente.setTelefono(rs.getString("Telefono"));
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion(connection);
        }

        return clientes;
    }

    @Override
    public Cliente getClienteById(int id) {
        Cliente cliente = null;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();

        try {
            PreparedStatement stmt = connection.prepareStatement(clienteRepositoryHelper.getClienteById());
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente(
                        rs.getInt("ClienteId"),
                        rs.getString("Nombre"),
                        rs.getString("Email"),
                        rs.getString("Telefono")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion(connection);
        }

        return cliente;
    }

    @Override
    public List<Cliente> getClientesByNombre(String nombre) {
        List<Cliente> clientes = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();

        try {
            PreparedStatement stmt = connection.prepareStatement(clienteRepositoryHelper.getClientesByNombre(nombre));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("ClienteId"),
                        rs.getString("Nombre"),
                        rs.getString("Email"),
                        rs.getString("Telefono")
                );
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion(connection);
        }

        return clientes;
    }

    @Override
    public boolean insertarCliente(Cliente cliente) {
        boolean result = false;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();

        try {
            PreparedStatement stmt = connection.prepareStatement(clienteRepositoryHelper.insertarCliente());
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefono());

            int filas = stmt.executeUpdate();
            result = filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion(connection);
        }

        return result;
    }

    @Override
    public boolean actualizarCliente(Cliente cliente) {
        boolean result = false;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();

        try {
            PreparedStatement stmt = connection.prepareStatement(clienteRepositoryHelper.actualizarCliente());
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefono());
            stmt.setInt(4, cliente.getIdCliente());

            int filas = stmt.executeUpdate();
            result = filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion(connection);
        }

        return result;
    }

    @Override
    public boolean eliminarCliente(int id) {
        boolean result = false;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();

        try {
            PreparedStatement stmt = connection.prepareStatement(clienteRepositoryHelper.eliminarCliente());
            stmt.setInt(1, id);

            int filas = stmt.executeUpdate();
            result = filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarConexion(connection);
        }

        return result;
    }

    private void cerrarConexion(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
