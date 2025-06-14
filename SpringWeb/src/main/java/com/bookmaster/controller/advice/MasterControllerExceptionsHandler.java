package com.bookmaster.controller.advice;

import java.util.HashMap;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MasterControllerExceptionsHandler {
	
	@ExceptionHandler(NotFoundException.class)
	@ResponseBody
	public ResponseEntity<HashMap> customStatusCodeWithMsg(HttpStatus status, String errmsg, NotFoundException e) {
		System.out.println("\nController Advice method is called\n");
		HashMap map = new HashMap<String, String>();
			map.put("status", status.toString());
			map.put("status-code", status.value());
			map.put("exception", e.getCause());
			map.put("message", e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
	}
}
