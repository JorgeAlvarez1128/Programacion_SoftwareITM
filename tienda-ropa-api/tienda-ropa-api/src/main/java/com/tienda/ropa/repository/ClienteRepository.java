package com.tienda.ropa.repository;

import com.tienda.ropa.model.Cliente;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.*;
import java.sql.*;
import java.util.*;

@Repository
public class ClienteRepository {
    private final JdbcTemplate jdbc;

    private static final String BASE = "SELECT ClienteId, Nombre, Email, Telefono FROM Clientes";
    private static final String BY_ID = BASE + " WHERE ClienteId=?";
    private static final String INS = "INSERT INTO Clientes (Nombre, Email, Telefono) VALUES (?,?,?)";
    private static final String UPD = "UPDATE Clientes SET Nombre=?, Email=?, Telefono=? WHERE ClienteId=?";
    private static final String DEL = "DELETE FROM Clientes WHERE ClienteId=?";

    private static final RowMapper<Cliente> MAPPER = (rs,n) ->
            new Cliente(rs.getInt("ClienteId"),
                    rs.getString("Nombre"),
                    rs.getString("Email"),
                    rs.getString("Telefono"));

    public ClienteRepository(JdbcTemplate jdbc){ this.jdbc = jdbc; }

    public List<Cliente> findAll(){ return jdbc.query(BASE, MAPPER); }
    public Optional<Cliente> findById(int id){ return jdbc.query(BY_ID, MAPPER, id).stream().findFirst(); }
    public int create(Cliente c){
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            var ps = con.prepareStatement(INS, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getTelefono());
            return ps;
        }, kh);
        return kh.getKey().intValue();
    }
    public int update(int id, Cliente c){ return jdbc.update(UPD, c.getNombre(), c.getEmail(), c.getTelefono(), id); }
    public int delete(int id){ return jdbc.update(DEL, id); }
}
