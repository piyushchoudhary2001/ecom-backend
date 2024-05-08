package com.ecombackend.orderservice.dto;

import com.ecombackend.orderservice.model.OrderLineItems;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class  OrderRequest {
    private List<OrderLineItemsDto> orderLineItemsDtoList;

}
