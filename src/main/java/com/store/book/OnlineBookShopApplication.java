package com.store.book;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("com.store.book.common")
@OpenAPIDefinition(
		info = @Info(
				title = "Book-Shop-Service",
				version = "0.1"
		)
)
public class OnlineBookShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineBookShopApplication.class, args);
	}

}
