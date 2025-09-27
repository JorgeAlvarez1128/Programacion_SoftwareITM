package com.tienda.ropa.repository;

import com.tienda.ropa.model.Rol;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import java.sql.*;
import java.util.*;

@Repository
public class RolRepository {
    private final JdbcTemplate jdbc;

    private static final String SQL_FIND_ALL = "SELECT RoleId, Nombre FROM Roles";
    private static final String SQL_FIND_BY_ID = "SELECT RoleId, Nombre FROM Roles WHERE RoleId = ?";
    private static final String SQL_INSERT = "INSERT INTO Roles (Nombre) VALUES (?)";
    private static final String SQL_UPDATE = "UPDATE Roles SET Nombre=? WHERE RoleId=?";
    private static final String SQL_DELETE = "DELETE FROM Roles WHERE RoleId=?";

    private static final RowMapper<Rol> MAPPER = (rs, n) ->
            new Rol(rs.getInt("RoleId"), rs.getString("Nombre"));

    public RolRepository(JdbcTemplate jdbc){ this.jdbc = jdbc; }

    public List<Rol> findAll(){ return jdbc.query(SQL_FIND_ALL, MAPPER); }
    public Optional<Rol> findById(int id){
        return jdbc.query(SQL_FIND_BY_ID, MAPPER, id).stream().findFirst();
    }
    public int create(Rol rol){
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, rol.getNombre());
            return ps;
        }, kh);
        return kh.getKey().intValue();
    }
    public int update(int id, Rol rol){ return jdbc.update(SQL_UPDATE, rol.getNombre(), id); }
    public int delete(int id){ return jdbc.update(SQL_DELETE, id); }
}
