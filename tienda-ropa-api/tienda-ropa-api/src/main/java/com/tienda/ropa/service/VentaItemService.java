package com.tienda.ropa.service;
import com.tienda.ropa.model.VentaItem;
import com.tienda.ropa.repository.VentaItemRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VentaItemService {
    private final VentaItemRepository repo;
    public VentaItemService(VentaItemRepository repo){ this.repo = repo; }
    public List<VentaItem> list(){ return repo.findAll(); }
    public VentaItem get(int id){ return repo.findById(id).orElse(null); }
    public List<VentaItem> byVenta(int ventaId){ return repo.findByVenta(ventaId); }
    public int create(VentaItem vi){ return repo.create(vi); }
    public boolean update(int id, VentaItem vi){ return repo.update(id, vi) > 0; }
    public boolean delete(int id){ return repo.delete(id) > 0; }
}
