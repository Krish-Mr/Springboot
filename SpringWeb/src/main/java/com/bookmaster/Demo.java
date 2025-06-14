package com.bookmaster;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
public class Demo {
	public static void main(String[] args) throws IOException {

		ConfigurableApplicationContext run = SpringApplication.run(Demo.class, args);
//		DatabaseOperation bean = run.getBean(DatabaseOperation.class);
//		List<String> informationSchemas = bean.getInformationSchemas("BookDetails", "COLUMN_NAME");
//		System.out.println(informationSchemas);
		/**
		 * use this for find the deployed path jar | war
		 */
		System.out.println("nio Path: " + Paths.get("").toAbsolutePath());
		System.out.println(" Class path: "+new ClassPathResource("").getFile().getAbsolutePath());
	}
}