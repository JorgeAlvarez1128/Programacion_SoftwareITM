package com.co.TrabajoProgramacion.TiendaRopa.services;

import com.co.TrabajoProgramacion.TiendaRopa.dto.venta.VentaCreateDTO;
import com.co.TrabajoProgramacion.TiendaRopa.dto.venta.VentaResponseDTO;
import com.co.TrabajoProgramacion.TiendaRopa.entities.*;
import com.co.TrabajoProgramacion.TiendaRopa.repositories.jpa.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService {

    private final ClienteJpaRepository clienteRepo;
    private final ProductoJpaRepository productoRepo;
    private final VentaJpaRepository ventaRepo;
    private final VentaItemJpaRepository ventaItemRepo;

    public VentaService(ClienteJpaRepository c, ProductoJpaRepository p,
                        VentaJpaRepository v, VentaItemJpaRepository vi) {
        this.clienteRepo = c;
        this.productoRepo = p;
        this.ventaRepo = v;
        this.ventaItemRepo = vi;
    }

    @Transactional
    public VentaResponseDTO crearVenta(VentaCreateDTO dto) {
        var cliente  = clienteRepo.findById(dto.clienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no existe"));
        var producto = productoRepo.findById(dto.productoId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no existe"));

        if (producto.getPrecioVenta() == null || producto.getPrecioVenta() <= 0) {
            throw new IllegalStateException("Producto sin precio válido");
        }
        if (dto.cantidad() <= 0) {
            throw new IllegalArgumentException("Cantidad debe ser mayor que cero");
        }

        // 1) Encabezado
        var venta = new Venta();
        venta.setCliente(cliente);
        venta.setFechaVenta(LocalDateTime.now());
        double total = dto.cantidad() * producto.getPrecioVenta();
        venta.setTotal(total);
        venta = ventaRepo.save(venta);

        // 2) Item
        var item = new VentaItem();
        item.setVenta(venta);
        item.setProducto(producto);
        item.setCantidad(dto.cantidad());
        item.setPrecioUnit(producto.getPrecioVenta());
        ventaItemRepo.save(item);

        return new VentaResponseDTO(
                venta.getId(),
                cliente.getIdCliente(), cliente.getNombre(),
                producto.getProductoId(), producto.getNombre(),
                dto.cantidad(),
                venta.getFechaVenta(),
                producto.getPrecioVenta(),
                total
        );
    }

    public List<VentaResponseDTO> listarPorCliente(Integer clienteId) {
        var ventas = ventaRepo.findByCliente_IdCliente(clienteId);
        var out = new ArrayList<VentaResponseDTO>();
        for (var v : ventas) {
            var items = ventaItemRepo.findByVenta_Id(v.getId());
            for (var it : items) {
                var p = it.getProducto();
                out.add(new VentaResponseDTO(
                        v.getId(),
                        v.getCliente().getIdCliente(), v.getCliente().getNombre(),
                        p.getProductoId(), p.getNombre(),
                        it.getCantidad(),
                        v.getFechaVenta(),
                        it.getPrecioUnit(),
                        v.getTotal() // si quisieras subtotal por item, usa it.getSubtotal()
                ));
            }
        }
        return out;
    }
}
