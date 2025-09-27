package com.tienda.ropa.service;

import com.tienda.ropa.dto.VentaRequest;
import com.tienda.ropa.model.Venta;
import com.tienda.ropa.model.VentaItem;
import com.tienda.ropa.repository.VentaItemRepository;
import com.tienda.ropa.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VentaService {
    private final VentaRepository ventaRepo;
    private final VentaItemRepository itemRepo;

    public VentaService(VentaRepository v, VentaItemRepository i){
        this.ventaRepo = v; this.itemRepo = i;
    }

    public List<Venta> list(){ return ventaRepo.findAll(); }
    public Venta get(int id){ return ventaRepo.findById(id).orElse(null); }
    public boolean update(int id, Venta v){ return ventaRepo.update(id, v) > 0; }
    public boolean delete(int id){ return ventaRepo.delete(id) > 0; }

    /** Crea la venta + items en transacción y calcula total. */
    @Transactional
    public int crearVenta(VentaRequest req){
        BigDecimal total = req.items().stream()
                .map(i -> i.precioUnit().multiply(BigDecimal.valueOf(i.cantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int ventaId = ventaRepo.create(new Venta(null, req.clienteId(), LocalDateTime.now(), total));

        for (var it : req.items()){
            itemRepo.create(new VentaItem(null, ventaId, it.productoId(), it.cantidad(), it.precioUnit(),
                    it.precioUnit().multiply(BigDecimal.valueOf(it.cantidad()))));
        }
        return ventaId;
    }
}
