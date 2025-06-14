package com.bookmaster.beanscope;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FavIconImg {

	public FavIconImg() {
		System.err.println("Favicon Class Created to avoid favicon.ico bad request");
	}
	
	@RequestMapping("/favicon.ico")
	public void avoidFaviconReq() {
		// nothing...
	}
}
