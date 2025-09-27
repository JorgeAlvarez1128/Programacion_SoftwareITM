package com.tienda.ropa.repository;

import com.tienda.ropa.model.Producto;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.support.*;

import java.sql.*;
import java.util.*;
import java.math.BigDecimal;

@Repository
public class ProductoRepository {
    private final JdbcTemplate jdbc;

    private static final String BASE = """
        SELECT ProductoId, CodigoBarra, Nombre, CategoriaId, Talla, Color, ProveedorId, PrecioVenta
        FROM Productos
        """;
    private static final String SQL_BY_ID = BASE + " WHERE ProductoId=?";
    private static final String SQL_INSERT = """
        INSERT INTO Productos (CodigoBarra, Nombre, CategoriaId, Talla, Color, ProveedorId, PrecioVenta)
        VALUES (?,?,?,?,?,?,?)
        """;
    private static final String SQL_UPDATE = """
        UPDATE Productos SET CodigoBarra=?, Nombre=?, CategoriaId=?, Talla=?, Color=?, ProveedorId=?, PrecioVenta=?
        WHERE ProductoId=?
        """;
    private static final String SQL_DELETE = "DELETE FROM Productos WHERE ProductoId=?";
    private static final String SQL_BY_CATEGORIA = BASE + " WHERE CategoriaId=?";

    private static final RowMapper<Producto> MAPPER = (rs,n) -> {
        Producto p = new Producto();
        p.setProductoId(rs.getInt("ProductoId"));
        p.setCodigoBarra(rs.getString("CodigoBarra"));
        p.setNombre(rs.getString("Nombre"));
        p.setCategoriaId(rs.getInt("CategoriaId"));
        p.setTalla(rs.getString("Talla"));
        p.setColor(rs.getString("Color"));
        p.setProveedorId(rs.getInt("ProveedorId"));
        p.setPrecioVenta(rs.getBigDecimal("PrecioVenta"));
        return p;
    };

    public ProductoRepository(JdbcTemplate jdbc){ this.jdbc = jdbc; }

    public List<Producto> findAll(){ return jdbc.query(BASE, MAPPER); }
    public Optional<Producto> findById(int id){ return jdbc.query(SQL_BY_ID, MAPPER, id).stream().findFirst(); }
    public List<Producto> findByCategoria(int categoriaId){ return jdbc.query(SQL_BY_CATEGORIA, MAPPER, categoriaId); }

    public int create(Producto p){
        KeyHolder kh = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, p.getCodigoBarra());
            ps.setString(2, p.getNombre());
            ps.setObject(3, p.getCategoriaId(), Types.INTEGER);
            ps.setString(4, p.getTalla());
            ps.setString(5, p.getColor());
            ps.setObject(6, p.getProveedorId(), Types.INTEGER);
            ps.setBigDecimal(7, p.getPrecioVenta()==null?BigDecimal.ZERO:p.getPrecioVenta());
            return ps;
        }, kh);
        return kh.getKey().intValue();
    }

    public int update(int id, Producto p){
        return jdbc.update(SQL_UPDATE, p.getCodigoBarra(), p.getNombre(),
                p.getCategoriaId(), p.getTalla(), p.getColor(), p.getProveedorId(),
                p.getPrecioVenta(), id);
    }
    public int delete(int id){ return jdbc.update(SQL_DELETE, id); }
}
