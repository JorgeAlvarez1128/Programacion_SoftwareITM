package com.co.TrabajoProgramacion.TiendaRopa.repositories.jpa;
import com.co.TrabajoProgramacion.TiendaRopa.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VentaJpaRepository extends JpaRepository<Venta, Integer> {
    List<Venta> findByCliente_IdCliente(Integer clienteId);
}