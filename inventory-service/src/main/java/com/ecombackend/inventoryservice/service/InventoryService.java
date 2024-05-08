package com.ecombackend.inventoryservice.service;

import com.ecombackend.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode)
    {
         return inventoryRepository.findByskucode(skuCode).isPresent();
    }
}
