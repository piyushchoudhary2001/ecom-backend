package com.ecombackend.inventoryservice.service;

import com.ecombackend.inventoryservice.dto.InventoryResponse;
import com.ecombackend.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode)
    {
         return inventoryRepository.findByskucodeIn(skuCode).stream()
                 .map(inventory ->
                     InventoryResponse.builder()
                             .skuCode(inventory.getSkucode())
                             .isInStock(inventory.getQuantity()>0)
                             .build()
                 ).toList();
    }
}
