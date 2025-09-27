package com.tienda.ropa.repository;

import com.tienda.ropa.model.Venta;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.math.BigDecimal;

@Repository
public class VentaRepository {
    private final JdbcTemplate jdbc;

    private static final String BASE = """
        SELECT VentaId, ClienteId, FechaVenta, Total
        FROM Ventas
        """;
    private static final String BY_ID = BASE + " WHERE VentaId=?";
    private static final String INS = """
        INSERT INTO Ventas (ClienteId, FechaVenta, Total)
        VALUES (?, COALESCE(?, SYSUTCDATETIME()), ?)
        """;
    private static final String UPD = "UPDATE Ventas SET ClienteId=?, FechaVenta=?, Total=? WHERE VentaId=?";
    private static final String DEL = "DELETE FROM Ventas WHERE VentaId=?";

    private static final RowMapper<Venta> MAPPER = (rs,n) -> new Venta(
            rs.getInt("VentaId"),
            rs.getInt("ClienteId"),
            rs.getTimestamp("FechaVenta").toLocalDateTime(),
            rs.getBigDecimal("Total")
    );

    public VentaRepository(JdbcTemplate jdbc){ this.jdbc = jdbc; }

    public List<Venta> findAll(){ return jdbc.query(BASE, MAPPER); }
    public Optional<Venta> findById(int id){ return jdbc.query(BY_ID, MAPPER, id).stream().findFirst(); }

    public int create(Venta v){
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            var ps = con.prepareStatement(INS, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, v.getClienteId());
            ps.setObject(2, v.getFechaVenta()==null?null:Timestamp.valueOf(v.getFechaVenta()));
            ps.setBigDecimal(3, v.getTotal()==null?BigDecimal.ZERO:v.getTotal());
            return ps;
        }, kh);
        return kh.getKey().intValue();
    }

    public int update(int id, Venta v){
        return jdbc.update(UPD, v.getClienteId(),
                Timestamp.valueOf(v.getFechaVenta()==null? LocalDateTime.now(): v.getFechaVenta()),
                v.getTotal(), id);
    }
    public int delete(int id){ return jdbc.update(DEL, id); }
}
