package com.ecombackend.inventoryservice.repository;

import com.ecombackend.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {


    List<Inventory> findByskucodeIn(List<String> skuCode);
}
