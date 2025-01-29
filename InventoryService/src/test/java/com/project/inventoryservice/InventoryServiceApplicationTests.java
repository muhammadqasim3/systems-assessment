package com.project.inventoryservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@OpenAPIDefinition(
		info = @Info(
				title = "Inventory Service API",
				version = "1.0",
				description = "API for managing inventory stock levels"
		)
)
@SpringBootTest
class InventoryServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
