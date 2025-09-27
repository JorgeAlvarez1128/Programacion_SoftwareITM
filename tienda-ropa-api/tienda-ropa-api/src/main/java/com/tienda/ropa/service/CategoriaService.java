package com.tienda.ropa.service;
import com.tienda.ropa.model.Categoria;
import com.tienda.ropa.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaService {
    private final CategoriaRepository repo;
    public CategoriaService(CategoriaRepository repo){ this.repo = repo; }
    public List<Categoria> list(){ return repo.findAll(); }
    public Categoria get(int id){ return repo.findById(id).orElse(null); }
    public int create(Categoria c){ return repo.create(c); }
    public boolean update(int id, Categoria c){ return repo.update(id, c) > 0; }
    public boolean delete(int id){ return repo.delete(id) > 0; }
}
