package com.tienda.ropa.repository;

import com.tienda.ropa.model.Proveedor;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.*;

import java.sql.*;
import java.util.*;

@Repository
public class ProveedorRepository {
    private final JdbcTemplate jdbc;

    private static final String SQL_ALL = "SELECT ProveedorId, Nombre, Contacto, Telefono FROM Proveedores";
    private static final String SQL_BY_ID = SQL_ALL + " WHERE ProveedorId=?";
    private static final String SQL_INS = "INSERT INTO Proveedores (Nombre, Contacto, Telefono) VALUES (?,?,?)";
    private static final String SQL_UPD = "UPDATE Proveedores SET Nombre=?, Contacto=?, Telefono=? WHERE ProveedorId=?";
    private static final String SQL_DEL = "DELETE FROM Proveedores WHERE ProveedorId=?";

    private static final RowMapper<Proveedor> MAPPER = (rs,n) ->
            new Proveedor(rs.getInt("ProveedorId"),
                    rs.getString("Nombre"),
                    rs.getString("Contacto"),
                    rs.getString("Telefono"));

    public ProveedorRepository(JdbcTemplate jdbc){ this.jdbc = jdbc; }

    public List<Proveedor> findAll(){ return jdbc.query(SQL_ALL, MAPPER); }
    public Optional<Proveedor> findById(int id){ return jdbc.query(SQL_BY_ID, MAPPER, id).stream().findFirst(); }
    public int create(Proveedor p){
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            var ps = con.prepareStatement(SQL_INS, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getContacto());
            ps.setString(3, p.getTelefono());
            return ps;
        }, kh);
        return kh.getKey().intValue();
    }
    public int update(int id, Proveedor p){ return jdbc.update(SQL_UPD, p.getNombre(), p.getContacto(), p.getTelefono(), id); }
    public int delete(int id){ return jdbc.update(SQL_DEL, id); }
}
