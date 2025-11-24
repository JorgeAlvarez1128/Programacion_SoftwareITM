package com.co.TrabajoProgramacion.TiendaRopa.repositories.jpa;

import com.co.TrabajoProgramacion.TiendaRopa.entities.VentaItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaItemJpaRepository extends JpaRepository<VentaItem, Integer> {

    // Para obtener los items de una venta
    List<VentaItem> findByVenta_Id(Integer ventaId);
}
