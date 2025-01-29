package com.project.orderservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@OpenAPIDefinition(
		info = @Info(
				title = "Order Service API",
				version = "1.0",
				description = "API for managing orders and verifying inventory availability"
		)
)
@SpringBootTest
class OrderServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
