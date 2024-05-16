package com.henryma.flashsale;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.henryma.FlashSale.db.mappers")
@ComponentScan(basePackages = {"com.henryma"})
public class FlashSaleApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlashSaleApplication.class, args);
	}

}
