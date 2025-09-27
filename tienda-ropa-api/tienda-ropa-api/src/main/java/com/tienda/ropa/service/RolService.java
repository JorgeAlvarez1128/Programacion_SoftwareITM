package com.tienda.ropa.service;

import com.tienda.ropa.model.Rol;
import com.tienda.ropa.repository.RolRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RolService {
    private final RolRepository repo;
    public RolService(RolRepository repo){ this.repo = repo; }
    public List<Rol> list(){ return repo.findAll(); }
    public Rol get(int id){ return repo.findById(id).orElse(null); }
    public int create(Rol r){ return repo.create(r); }
    public boolean update(int id, Rol r){ return repo.update(id, r) > 0; }
    public boolean delete(int id){ return repo.delete(id) > 0; }
}
