package com.eureka.client1.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Client1Config {
	@Bean("load-rest")
	@LoadBalanced
	public RestTemplate getLoadBalanceRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean("load-client")
	@LoadBalanced
	public WebClient getLoadBalanceWebClient() {
		return WebClient.create();
	}
	
	@Bean("rest")
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean("client")
	public WebClient getWebClient() {
		return WebClient.create();
	}
}
