package com.tienda.ropa.repository;

import com.tienda.ropa.model.Inventario;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.*;
import java.sql.*;
import java.util.*;

@Repository
public class InventarioRepository {
    private final JdbcTemplate jdbc;

    private static final String BASE = "SELECT InventarioId, ProductoId, Cantidad, Ubicacion FROM Inventario";
    private static final String BY_ID = BASE + " WHERE InventarioId=?";
    private static final String INS = "INSERT INTO Inventario (ProductoId, Cantidad, Ubicacion) VALUES (?,?,?)";
    private static final String UPD = "UPDATE Inventario SET ProductoId=?, Cantidad=?, Ubicacion=? WHERE InventarioId=?";
    private static final String DEL = "DELETE FROM Inventario WHERE InventarioId=?";

    private static final RowMapper<Inventario> MAPPER = (rs,n) ->
            new Inventario(rs.getInt("InventarioId"),
                    rs.getInt("ProductoId"),
                    rs.getInt("Cantidad"),
                    rs.getString("Ubicacion"));

    public InventarioRepository(JdbcTemplate jdbc){ this.jdbc = jdbc; }

    public List<Inventario> findAll(){ return jdbc.query(BASE, MAPPER); }
    public Optional<Inventario> findById(int id){ return jdbc.query(BY_ID, MAPPER, id).stream().findFirst(); }
    public int create(Inventario i){
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            var ps = con.prepareStatement(INS, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, i.getProductoId());
            ps.setInt(2, i.getCantidad());
            ps.setString(3, i.getUbicacion());
            return ps;
        }, kh);
        return kh.getKey().intValue();
    }
    public int update(int id, Inventario i){
        return jdbc.update(UPD, i.getProductoId(), i.getCantidad(), i.getUbicacion(), id);
    }
    public int delete(int id){ return jdbc.update(DEL, id); }
}
