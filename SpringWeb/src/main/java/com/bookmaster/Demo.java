package com.bookmaster;

import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication(scanBasePackages = "com.bookmaster.aop",exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class})
//@Configuration
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class,DataSourceTransactionManagerAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class})
//@ComponentScan(
//    includeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.bookmaster\\.aop\\..*")
//)
public class Demo {
	public static void main(String[] args) throws IOException {

		ConfigurableApplicationContext run = SpringApplication.run(Demo.class, args);
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
		for(String beanDef  : annotationConfigApplicationContext.getBeanDefinitionNames()) {
			System.out.println(beanDef);
		}
		/**
		 * use this for find the deployed path jar | war
		 */
		System.out.println("nio Path: " + Paths.get("").toAbsolutePath());
		System.out.println(" Class path: "+new ClassPathResource("").getFile().getAbsolutePath());
	}
}