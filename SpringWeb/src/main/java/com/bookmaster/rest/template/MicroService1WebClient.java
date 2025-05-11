package com.bookmaster.rest.template;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/microservice1")
public class MicroService1WebClient {
	@GetMapping("/webclient")
	public void callMicroServiceUsingWebClient() {
		
	}

	@GetMapping("/rest-template")
	public void callMicroServiceUsingRestTemplate() {
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> getReq = rt.getForEntity("http://localhost:8081/", String.class);
		System.out.println("Get Request Executed: " + getReq.getBody());
		
	}
	
	
	public void restClient() {

	}
	
	public void monoFlux() {
		System.out.println("callMicroService invoked");
		WebClient create = WebClient.create("http://localhost:8081/");
		Mono<ResponseEntity<String>> entity = WebClient.builder().build().get().uri("/").retrieve().toEntity(String.class);
		System.out.println(entity);		create.put();

		create.delete();
		
	};
}
