package com.ecombackend.productservice.controller;

import com.ecombackend.productservice.dto.request.ProductRequest;
import com.ecombackend.productservice.dto.responce.ProductResponce;
import com.ecombackend.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest)
    {
        try {
            productService.createProduct(productRequest);
            return new ResponseEntity<>("Product Created Successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllProduct()
    {
        try {
            List<ProductResponce> productResponceList = productService.getAllProduct();
            return new ResponseEntity<>(productResponceList, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
