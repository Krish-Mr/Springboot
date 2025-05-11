package com.bookmaster.config.jwt.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("jwt-user")
public class JwtUserController {

	@Autowired
	JwtService jwtService;

	@Autowired
	JwtUserService jwtUserServ;

	@PostMapping("/add")
	private ResponseEntity<JwtUserDetails> addUser(@RequestBody JwtUserDetails user) {
		JwtUserDetails storeUser = jwtUserServ.storeUser(user);
		if(storeUser != null) {
			return ResponseEntity.status(HttpStatus.CREATED).body(user);
		}else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(user);
		}
	}

	@GetMapping("/token-generator")
	private ResponseEntity<?> getToken(@RequestParam String user) {
		try {
			UserDetails userProfile = jwtUserServ.loadUserByUsername(user);
			String token =  jwtService.generateToken(userProfile.getUsername());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
		} catch (UsernameNotFoundException u) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(u.getMessage());
		}
	}

	/**
		* security for enable Authorize globally to all end-points
	 */
	@Operation(summary = "Validate JWT Token", security = @SecurityRequirement(name = "bearerAuth"))
	@PostMapping("/auth")
	public boolean authendicateUser(
			@Parameter(name = "Jwt_Authorization", description = "provide jwt token here for specific end point", required = true, in = ParameterIn.HEADER,  schema = @Schema(type = "string"))
			@RequestHeader(name = "Jwt_Authorization", required = false) String authorization, @RequestParam("user") String user) {
		UserDetails userProfile = jwtUserServ.loadUserByUsername(user);
		return jwtService.validateToken(authorization, userProfile.getUsername());
	}

	private class Auth{
		private String userName;
		private String pass;
	}
}
