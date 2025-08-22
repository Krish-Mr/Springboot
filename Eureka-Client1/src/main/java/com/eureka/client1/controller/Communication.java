package com.eureka.client1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.eureka.client1.component.ServiceEventListener;

@RestController
@RequestMapping("/call")
public class Communication {
	
	@Autowired
	private ServiceEventListener event;
	
	@Autowired
	RestTemplate rest;
	@Autowired
	WebClient client;
	/**
	 * On calling one microservice to another microservice
	 * @LoadBalanced is used to identify the endpoints with the help of <http:/applicationName/path>
	 * No need to provide a full path like <http://localhost:8080/applicationName/path>
	 */
	@Autowired
	@Qualifier("load-rest")
	RestTemplate loadRest;
	@Autowired
	@Qualifier("load-client")
	WebClient loadClient;
	
	@GetMapping("/rest")
	public String callClient_2_rest(){
		String res = rest.getForObject( "http://localhost:8080/eureka-client2/provider/rest-template", String.class);
		return "<h1>Client 1 -> <"+event.getPort()+"></h1>"+res;
	}
	@GetMapping("/load/rest")
	public String callClient_2_loadRest(){
		String res = loadRest.getForObject( "http://eureka-client2/provider/rest-template", String.class);
		return "<h1>Client 1 -> </h1>"+res;
	}

	@GetMapping("/client")
	public String callClient_2_client(){
		String res = client.get().uri( "http://localhost:8080/eureka-client2/provider/web-client").retrieve().bodyToMono(String.class).block();
		return "<h1>Client 1 -> </h1>"+res;
	}
	@GetMapping("/load/client")
	public String callClient_2_loadClient(){
		String res = loadClient.get().uri( "http://eureka-client2/provider/web-client").retrieve().bodyToMono(String.class).block();
		return "<h1>Client 1 -> </h1>"+res;
	}
}