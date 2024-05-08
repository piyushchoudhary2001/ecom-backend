package com.ecombackend.productservice;
//here we are doing integration testing using (testcontainer:- testing tool for integration testing)
import com.ecombackend.productservice.dto.ProductRequest;
import com.ecombackend.productservice.dto.ProductResponse;
import com.ecombackend.productservice.model.Product;
import com.ecombackend.productservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.apache.commons.compress.harmony.pack200.PackingUtils.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	//here we are creating a container using testcontainer which ultimately creating a docker image of mongodb 
	@Container
	static MongoDBContainer mongoDBContainer=new MongoDBContainer("mongo:4.4.2");


	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	ProductRepository productRepository;

	@DynamicPropertySource
	static  void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry)
	{
		dynamicPropertyRegistry.add("spring.data.mongodb.uri",mongoDBContainer::getReplicaSetUrl);
	}
	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest=getProductRequest();
		String productRequestString=objectMapper.writeValueAsString(productRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/product/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productRequestString))
				.andExpect(
						status().isCreated()
				);
        Assertions.assertEquals(1, productRepository.findAll().size());
		log("Product created successfully");
		mockMvc.perform(MockMvcRequestBuilders.get("/api/product/get"))
				.andExpect(
						status().isOk()
				);
		log("Product received successfully");
	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("iPhone 13")
				.description("iPhone 13")
				.price("80000")
				.build();
	}

}
