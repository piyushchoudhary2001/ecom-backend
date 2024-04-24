package com.ecombackend.inventoryservice.repository;

import com.ecombackend.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {
    Optional<Inventory> findByskucode(String skucode);
}
