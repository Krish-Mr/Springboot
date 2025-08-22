package com.eureka.client2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class EurekaClient2Application {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClient2Application.class, args);
	}
}