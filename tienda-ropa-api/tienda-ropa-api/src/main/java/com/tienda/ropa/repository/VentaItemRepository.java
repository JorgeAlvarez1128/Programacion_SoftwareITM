package com.tienda.ropa.repository;

import com.tienda.ropa.model.VentaItem;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.*;
import java.sql.*;
import java.util.*;
import java.math.BigDecimal;

@Repository
public class VentaItemRepository {
    private final JdbcTemplate jdbc;

    private static final String BASE = """
        SELECT VentaItemId, VentaId, ProductoId, Cantidad, PrecioUnit,
               CAST(Cantidad*PrecioUnit AS DECIMAL(10,2)) AS Subtotal
        FROM VentaItems
        """;
    private static final String BY_ID = BASE + " WHERE VentaItemId=?";
    private static final String BY_VENTA = BASE + " WHERE VentaId=?";
    private static final String INS = "INSERT INTO VentaItems (VentaId, ProductoId, Cantidad, PrecioUnit) VALUES (?,?,?,?)";
    private static final String UPD = "UPDATE VentaItems SET VentaId=?, ProductoId=?, Cantidad=?, PrecioUnit=? WHERE VentaItemId=?";
    private static final String DEL = "DELETE FROM VentaItems WHERE VentaItemId=?";

    private static final RowMapper<VentaItem> MAPPER = (rs,n) -> new VentaItem(
            rs.getInt("VentaItemId"),
            rs.getInt("VentaId"),
            rs.getInt("ProductoId"),
            rs.getInt("Cantidad"),
            rs.getBigDecimal("PrecioUnit"),
            rs.getBigDecimal("Subtotal")
    );

    public VentaItemRepository(JdbcTemplate jdbc){ this.jdbc = jdbc; }

    public List<VentaItem> findAll(){ return jdbc.query(BASE, MAPPER); }
    public Optional<VentaItem> findById(int id){ return jdbc.query(BY_ID, MAPPER, id).stream().findFirst(); }
    public List<VentaItem> findByVenta(int ventaId){ return jdbc.query(BY_VENTA, MAPPER, ventaId); }

    public int create(VentaItem vi){
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            var ps = con.prepareStatement(INS, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, vi.getVentaId());
            ps.setInt(2, vi.getProductoId());
            ps.setInt(3, vi.getCantidad());
            ps.setBigDecimal(4, vi.getPrecioUnit()==null?BigDecimal.ZERO:vi.getPrecioUnit());
            return ps;
        }, kh);
        return kh.getKey().intValue();
    }
    public int update(int id, VentaItem vi){
        return jdbc.update(UPD, vi.getVentaId(), vi.getProductoId(), vi.getCantidad(), vi.getPrecioUnit(), id);
    }
    public int delete(int id){ return jdbc.update(DEL, id); }
}
