package com.tienda.ropa.service;
import com.tienda.ropa.model.Inventario;
import com.tienda.ropa.repository.InventarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InventarioService {
    private final InventarioRepository repo;
    public InventarioService(InventarioRepository repo){ this.repo = repo; }
    public List<Inventario> list(){ return repo.findAll(); }
    public Inventario get(int id){ return repo.findById(id).orElse(null); }
    public int create(Inventario i){ return repo.create(i); }
    public boolean update(int id, Inventario i){ return repo.update(id, i) > 0; }
    public boolean delete(int id){ return repo.delete(id) > 0; }
}
