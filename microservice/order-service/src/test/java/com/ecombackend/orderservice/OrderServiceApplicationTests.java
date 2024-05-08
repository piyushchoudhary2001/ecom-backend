package com.ecombackend.orderservice;

import com.ecombackend.orderservice.dto.OrderLineItemsDto;
import com.ecombackend.orderservice.dto.OrderRequest;
import com.ecombackend.orderservice.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest
class OrderServiceApplicationTests {

	static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:16");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	OrderRepository orderRepository;



	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		postgreSQLContainer.start();
		dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
		dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
		dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
	}


	@Test
	void testPlaceOrder() throws Exception {
		OrderRequest orderRequest = getOrderRequest();
		String orderRequestString = objectMapper.writeValueAsString(orderRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/order")
				.contentType(MediaType.APPLICATION_JSON)
				.content(orderRequestString)).andExpect(
				status().isCreated()
		);

		Assertions.assertEquals(1, orderRepository.findAll().size());
		log.info("order-service tested successfully");
	}

	private OrderRequest getOrderRequest() {
		return OrderRequest.builder()
				.orderLineItemsDtoList(orderLineDto())
				.build();
	}

	private List<OrderLineItemsDto> orderLineDto() {
		OrderLineItemsDto orderLineItemsDto = OrderLineItemsDto.builder()
				.skuCode("i_phone13")
				.price(BigDecimal.valueOf(1200))
				.quantity(1)
				.Id(1L)
				.build();
		List<OrderLineItemsDto> orderLineItemsDtoList=new ArrayList<>();
		orderLineItemsDtoList.add(orderLineItemsDto);
		return orderLineItemsDtoList;

	}


}
