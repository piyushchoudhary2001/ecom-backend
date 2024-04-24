package com.ecombackend.orderservice.service;

import com.ecombackend.orderservice.dto.InventoryResponse;
import com.ecombackend.orderservice.dto.OrderLineItemsDto;
import com.ecombackend.orderservice.dto.OrderRequest;
import com.ecombackend.orderservice.model.Order;
import com.ecombackend.orderservice.model.OrderLineItems;
import com.ecombackend.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

   private final OrderRepository orderRepository;
   private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest){
        Order order =new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems=orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);


        List<String> skuCodes=order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        //call inventory service, and place order if product is in
        //stock

       InventoryResponse[] inventoryResponsesArray= webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build() )
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

       boolean allProductInStock= Arrays.stream(inventoryResponsesArray)
               .allMatch(InventoryResponse::isInStock);

        if(Boolean.TRUE.equals(allProductInStock))
        {
            orderRepository.save(order);
        }else {
            throw new IllegalArgumentException("Products is not in stock, please try after some time");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems=new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
       return  orderLineItems;
    }
}
