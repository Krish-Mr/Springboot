package com.bookmaster.rest.template;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import reactor.core.publisher.Flux;
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
		WebClient webReq = WebClient.create("http://localhost:8081/");
		Mono<ResponseEntity<String>> entity = WebClient.builder().build().get().uri("/").retrieve().toEntity(String.class);
		System.out.println(entity);		
		
		
		ResponseSpec responseSpec = webReq.post().body(null).header(null, null).accept(MediaType.APPLICATION_JSON).retrieve();
			Mono<ResponseEntity<String>> toEntity = responseSpec.toEntity(String.class);
			
			Mono<String> bodyToMono = responseSpec.bodyToMono(String.class);
			Flux<String> bodyToFlux = responseSpec.bodyToFlux(String.class);
				bodyToFlux.blockFirst();
		webReq.get();
		webReq.put();
		webReq.delete();
		
	};
}
