package com.tienda.ropa.service;
import com.tienda.ropa.model.Producto;
import com.tienda.ropa.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {
    private final ProductoRepository repo;
    public ProductoService(ProductoRepository repo){ this.repo = repo; }
    public List<Producto> list(){ return repo.findAll(); }
    public Producto get(int id){ return repo.findById(id).orElse(null); }
    public List<Producto> byCategoria(int categoriaId){ return repo.findByCategoria(categoriaId); }
    public int create(Producto p){ return repo.create(p); }
    public boolean update(int id, Producto p){ return repo.update(id, p) > 0; }
    public boolean delete(int id){ return repo.delete(id) > 0; }
}
