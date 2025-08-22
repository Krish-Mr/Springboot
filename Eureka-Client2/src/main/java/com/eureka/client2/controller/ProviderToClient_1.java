package com.eureka.client2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eureka.client2.component.ServiceEventListener;

@RestController
@RequestMapping("/provider")
public class ProviderToClient_1 {
	
	@Autowired
	ServiceEventListener event;
	
	@Value("${spring.application.name}")
	private String appName;
	
	@GetMapping("/rest-template")
	public String provideRestTemplate() {
		return String.format("<h3>Rest Template</h3></br>Application Name: %s </br>Application Port: %d", appName, event.getPort());
	}
	
	@GetMapping("/web-client")
	public String provideWebClient() {
		return String.format("<h3>Web-Client</h3></br>Application Name: %s </br>Application Port: %d", appName, event.getPort());
	}

}
