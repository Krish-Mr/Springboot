package com.bookmaster.aop.run;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aop")
public class AOPController {
	@GetMapping("/before")
	public ResponseEntity<?> before(){
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Before Method Called");
	}

	@GetMapping("/after")
	public ResponseEntity<?> after(){
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("After Method Called");
	}

	@GetMapping("/around")
	public ResponseEntity<?> around(){
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Around Method Called");
	}

	@GetMapping("/returning")
	public ResponseEntity<?> returning(){
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Returning Method Called");
	}

	@GetMapping("/throwing")
	public ResponseEntity<?> throwing() throws Exception{
		if(true)
			throw new Exception("controller exception for AOP after throwing");
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("Throwing Method Called");
	}
}