package com.ecombackend.productservice.service;

import com.ecombackend.productservice.dto.request.ProductRequest;
import com.ecombackend.productservice.dto.responce.ProductResponce;
import com.ecombackend.productservice.model.Product;
import com.ecombackend.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    TransformerService transformerService;

    @Autowired
    ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = transformerService.getProduct(productRequest);
        productRepository.save(product);
    }

    public List<ProductResponce> getAllProduct() {
        List<Product> productList = productRepository.findAll();
        return transformerService.getProductResponceList(productList);
    }
}
