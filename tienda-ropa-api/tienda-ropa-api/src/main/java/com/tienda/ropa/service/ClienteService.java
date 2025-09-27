package com.tienda.ropa.service;
import com.tienda.ropa.model.Cliente;
import com.tienda.ropa.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository repo;
    public ClienteService(ClienteRepository repo){ this.repo = repo; }
    public List<Cliente> list(){ return repo.findAll(); }
    public Cliente get(int id){ return repo.findById(id).orElse(null); }
    public int create(Cliente c){ return repo.create(c); }
    public boolean update(int id, Cliente c){ return repo.update(id, c) > 0; }
    public boolean delete(int id){ return repo.delete(id) > 0; }
}
