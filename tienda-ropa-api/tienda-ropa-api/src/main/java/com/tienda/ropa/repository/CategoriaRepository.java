package com.tienda.ropa.repository;

import com.tienda.ropa.model.Categoria;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.*;

import java.sql.*;
import java.util.*;

@Repository
public class CategoriaRepository {
    private final JdbcTemplate jdbc;

    private static final String SQL_FIND_ALL = "SELECT CategoriaId, Nombre FROM Categorias";
    private static final String SQL_FIND_BY_ID = "SELECT CategoriaId, Nombre FROM Categorias WHERE CategoriaId=?";
    private static final String SQL_INSERT = "INSERT INTO Categorias (Nombre) VALUES (?)";
    private static final String SQL_UPDATE = "UPDATE Categorias SET Nombre=? WHERE CategoriaId=?";
    private static final String SQL_DELETE = "DELETE FROM Categorias WHERE CategoriaId=?";

    private static final RowMapper<Categoria> MAPPER = (rs, n) ->
            new Categoria(rs.getInt("CategoriaId"), rs.getString("Nombre"));

    public CategoriaRepository(JdbcTemplate jdbc){ this.jdbc = jdbc; }

    public List<Categoria> findAll(){ return jdbc.query(SQL_FIND_ALL, MAPPER); }
    public Optional<Categoria> findById(int id){
        return jdbc.query(SQL_FIND_BY_ID, MAPPER, id).stream().findFirst();
    }
    public int create(Categoria c){
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            var ps = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, c.getNombre());
            return ps;
        }, kh);
        return kh.getKey().intValue();
    }
    public int update(int id, Categoria c){ return jdbc.update(SQL_UPDATE, c.getNombre(), id); }
    public int delete(int id){ return jdbc.update(SQL_DELETE, id); }
}
