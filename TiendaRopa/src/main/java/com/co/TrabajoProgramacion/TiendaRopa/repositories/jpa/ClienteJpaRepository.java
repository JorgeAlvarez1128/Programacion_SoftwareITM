package com.co.TrabajoProgramacion.TiendaRopa.repositories.jpa;
import com.co.TrabajoProgramacion.TiendaRopa.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClienteJpaRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
}