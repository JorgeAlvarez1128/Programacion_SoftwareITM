package com.co.TrabajoProgramacion.TiendaRopa.services;

import com.co.TrabajoProgramacion.TiendaRopa.dto.venta.VentaCreateDTO;
import com.co.TrabajoProgramacion.TiendaRopa.dto.venta.VentaResponseDTO;
import com.co.TrabajoProgramacion.TiendaRopa.entities.Producto;
import com.co.TrabajoProgramacion.TiendaRopa.entities.Venta;
import com.co.TrabajoProgramacion.TiendaRopa.entities.VentaItem;
import com.co.TrabajoProgramacion.TiendaRopa.repositories.jpa.ClienteJpaRepository;
import com.co.TrabajoProgramacion.TiendaRopa.repositories.jpa.ProductoJpaRepository;
import com.co.TrabajoProgramacion.TiendaRopa.repositories.jpa.VentaItemJpaRepository;
import com.co.TrabajoProgramacion.TiendaRopa.repositories.jpa.VentaJpaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class VentaService {

    private final ClienteJpaRepository clienteRepo;
    private final ProductoJpaRepository productoRepo;
    private final VentaJpaRepository   ventaRepo;
    private final VentaItemJpaRepository ventaItemRepo;

    public VentaService(ClienteJpaRepository clienteRepo,
                        ProductoJpaRepository productoRepo,
                        VentaJpaRepository ventaRepo,
                        VentaItemJpaRepository ventaItemRepo) {
        this.clienteRepo   = clienteRepo;
        this.productoRepo  = productoRepo;
        this.ventaRepo     = ventaRepo;
        this.ventaItemRepo = ventaItemRepo;
    }

    @Transactional
    public VentaResponseDTO crearVenta(VentaCreateDTO dto) {
        var cliente = clienteRepo.findById(dto.clienteId())
                .orElseThrow(() -> new IllegalArgumentException("Cliente no existe"));

        var producto = productoRepo.findById(dto.productoId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no existe"));

        if (producto.getPrecioVenta() == null || producto.getPrecioVenta() <= 0) {
            throw new IllegalStateException("Producto sin precio válido");
        }
        if (dto.cantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }

        var venta = new Venta();
        venta.setCliente(cliente);

        LocalDateTime fechaVenta = (dto.fechaVenta() != null)
                ? dto.fechaVenta().atStartOfDay()
                : LocalDateTime.now();
        venta.setFechaVenta(fechaVenta);

        double total = dto.cantidad() * producto.getPrecioVenta();
        venta.setTotal(total);
        venta = ventaRepo.save(venta);
        // Item de la venta
        var item = new VentaItem();
        item.setVenta(venta);
        item.setProducto(producto);
        item.setCantidad(dto.cantidad());
        item.setPrecioUnit(producto.getPrecioVenta());
        ventaItemRepo.save(item);

        return new VentaResponseDTO(
                venta.getId(),
                cliente.getIdCliente(),
                cliente.getNombre(),
                producto.getProductoId(),
                producto.getNombre(),
                dto.cantidad(),
                venta.getFechaVenta(),
                producto.getPrecioVenta(),
                venta.getTotal()
        );
    }

    public List<VentaResponseDTO> listarPorCliente(Integer clienteId) {
        var ventas = ventaRepo.findByCliente_IdCliente(clienteId);
        var out = new ArrayList<VentaResponseDTO>();

        for (Venta v : ventas) {
            var items = ventaItemRepo.findByVenta_Id(v.getId());
            for (VentaItem it : items) {
                Producto p = it.getProducto();
                out.add(new VentaResponseDTO(
                        v.getId(),
                        v.getCliente().getIdCliente(),
                        v.getCliente().getNombre(),
                        p.getProductoId(),
                        p.getNombre(),
                        it.getCantidad(),
                        v.getFechaVenta(),
                        it.getPrecioUnit(),
                        v.getTotal()
                ));
            }
        }
        return out;
    }
}
