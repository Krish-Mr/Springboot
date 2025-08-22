package com.eureka.client1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eureka.client1.component.ServiceEventListener;

@RestController
@RequestMapping("/api-1")
public class Eureka {
	@Value("${spring.application.name}")
	private String app;
	@Autowired
	private ServiceEventListener event;

	@GetMapping("/get")
	public String m1() {
		return "Client Service - 1 </br>"+"Application Name: "+app+" </br>Running in Port: "+event.getPort();
	}
}
