package com.ecombackend.productservice.service;

import com.ecombackend.productservice.dto.request.ProductRequest;
import com.ecombackend.productservice.dto.responce.ProductResponce;
import com.ecombackend.productservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransformerService {

    public Product getProduct(ProductRequest productRequest) {
        return Product.builder()
            .name(productRequest.getName())
            .description(productRequest.getDescription())
            .price(productRequest.getPrice())
            .build();
    }

    public List<ProductResponce> getProductResponceList(List<Product> productList) {
        return productList.stream()
            .map(product -> ProductResponce.builder()
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build())
            .toList();
    }
}
