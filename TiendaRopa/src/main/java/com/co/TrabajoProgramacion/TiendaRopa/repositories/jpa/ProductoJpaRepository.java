package com.co.TrabajoProgramacion.TiendaRopa.repositories.jpa;
import com.co.TrabajoProgramacion.TiendaRopa.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoJpaRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
}
