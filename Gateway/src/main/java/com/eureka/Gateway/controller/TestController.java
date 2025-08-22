package com.eureka.Gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@GetMapping
	public String m1() {
		return "Gateway Routing is up...";
	}
}
