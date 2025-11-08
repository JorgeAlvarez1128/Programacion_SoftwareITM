package com.co.TrabajoProgramacion.TiendaRopa.repositories;

import com.co.TrabajoProgramacion.TiendaRopa.entities.Producto;
import com.co.TrabajoProgramacion.TiendaRopa.utilities.Conexion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductoRepository implements ProductoRepositoryInterface {

    @Autowired
    private ProductoRepositoryHelper helper;

    @Override
    public List<Producto> getProductos() {
        List<Producto> productos = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();

        try {
            PreparedStatement stmt = connection.prepareStatement(helper.listarProductos());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) productos.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar(connection);
        }
        return productos;
    }

    @Override
    public Producto getProductoById(int idProducto) {
        Producto p = null;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();

        try {
            PreparedStatement stmt = connection.prepareStatement(helper.getProductoById());
            stmt.setInt(1, idProducto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) p = mapRow(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar(connection);
        }
        return p;
    }

    @Override
    public List<Producto> getProductosByNombre(String nombre) {
        List<Producto> productos = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();

        try {
            PreparedStatement stmt = connection.prepareStatement(helper.getProductosByNombre(nombre));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) productos.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar(connection);
        }
        return productos;
    }

    @Override
    public boolean insertarProducto(Producto p) {
        boolean result = false;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();

        try {
            PreparedStatement ps = connection.prepareStatement(helper.insertarProducto(), Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getCodigoBarra());
            ps.setString(2, p.getNombre());
            if (p.getCategoriaId() == null) ps.setNull(3, Types.INTEGER); else ps.setInt(3, p.getCategoriaId());
            ps.setString(4, p.getTalla());
            ps.setString(5, p.getColor());
            if (p.getProveedorId() == null) ps.setNull(6, Types.INTEGER); else ps.setInt(6, p.getProveedorId());
            if (p.getPrecioVenta() == null) ps.setNull(7, Types.DECIMAL); else ps.setDouble(7, p.getPrecioVenta());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) p.setProductoId(keys.getInt(1));
                }
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar(connection);
        }
        return result;
    }

    @Override
    public boolean actualizarProducto(Producto p) {
        boolean result = false;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();

        try {
            PreparedStatement ps = connection.prepareStatement(helper.actualizarProducto());
            ps.setString(1, p.getCodigoBarra());
            ps.setString(2, p.getNombre());
            if (p.getCategoriaId() == null) ps.setNull(3, Types.INTEGER); else ps.setInt(3, p.getCategoriaId());
            ps.setString(4, p.getTalla());
            ps.setString(5, p.getColor());
            if (p.getProveedorId() == null) ps.setNull(6, Types.INTEGER); else ps.setInt(6, p.getProveedorId());
            if (p.getPrecioVenta() == null) ps.setNull(7, Types.DECIMAL); else ps.setDouble(7, p.getPrecioVenta());
            ps.setInt(8, p.getProductoId());

            result = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar(connection);
        }
        return result;
    }

    @Override
    public boolean eliminarProducto(int idProducto) {
        boolean result = false;
        Conexion conexion = new Conexion();
        Connection connection = conexion.obtenerConexion();

        try {
            PreparedStatement ps = connection.prepareStatement(helper.eliminarProducto());
            ps.setInt(1, idProducto);
            result = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // puede fallar por FKs (Inventario/Ventas)
        } finally {
            cerrar(connection);
        }
        return result;
    }

    private Producto mapRow(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setProductoId(rs.getInt("ProductoId"));
        p.setCodigoBarra(rs.getString("CodigoBarra"));
        p.setNombre(rs.getString("Nombre"));
        int cat = rs.getInt("CategoriaId");   p.setCategoriaId(rs.wasNull() ? null : cat);
        p.setTalla(rs.getString("Talla"));
        p.setColor(rs.getString("Color"));
        int prov = rs.getInt("ProveedorId");  p.setProveedorId(rs.wasNull() ? null : prov);
        double precio = rs.getDouble("PrecioVenta"); p.setPrecioVenta(rs.wasNull() ? null : precio);
        return p;
    }

    private void cerrar(Connection connection) {
        try { if (connection != null && !connection.isClosed()) connection.close(); }
        catch (SQLException e) { System.out.println("Error al cerrar la conexión: " + e.getMessage()); }
    }
}
