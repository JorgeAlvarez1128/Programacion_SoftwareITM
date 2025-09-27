package com.tienda.ropa.service;
import com.tienda.ropa.model.Proveedor;
import com.tienda.ropa.repository.ProveedorRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProveedorService {
    private final ProveedorRepository repo;
    public ProveedorService(ProveedorRepository repo){ this.repo = repo; }
    public List<Proveedor> list(){ return repo.findAll(); }
    public Proveedor get(int id){ return repo.findById(id).orElse(null); }
    public int create(Proveedor p){ return repo.create(p); }
    public boolean update(int id, Proveedor p){ return repo.update(id, p) > 0; }
    public boolean delete(int id){ return repo.delete(id) > 0; }
}
