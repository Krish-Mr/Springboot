package com.eureka.client1.component;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ServiceEventListener {
	private int port;

	@EventListener
	public void endpoint(WebServerInitializedEvent event) {
		this.port = event.getWebServer().getPort();
	}

	public int getPort() {
		return this.port;
	}
}
