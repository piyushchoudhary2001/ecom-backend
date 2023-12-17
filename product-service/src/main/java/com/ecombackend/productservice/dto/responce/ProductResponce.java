package com.ecombackend.productservice.dto.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponce {
    private String name;
    private String description;
    private String price;
}
