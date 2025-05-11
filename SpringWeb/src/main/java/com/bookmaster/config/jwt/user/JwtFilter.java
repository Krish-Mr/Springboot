package com.bookmaster.config.jwt.user;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{
	
	public final AuthenticationManager authManager;
	public final JwtService jwtService;
	public final JwtUserService jwtUsrService;

	@Autowired 
	JwtFilter(AuthenticationManager authManager, JwtService jwtService, JwtUserService jwtUserService){
		this.authManager = authManager;
		this.jwtService = jwtService;
		this.jwtUsrService = jwtUserService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		System.out.println("do internal filter");
		System.out.println("Request: "+ request.getRequestURL() + " | "+request.getMethod());
		System.out.println("Response: "+ HttpStatus.resolve(response.getStatus()));
		System.out.println("Header: " + Collections.list(request.getHeaderNames()));
		String token = request.getHeader("authorization");
		System.out.println("Request: "+ token);
		System.out.println("Response: "+ response.getHeader("Authorization"));
		if(request.getRequestURL().substring(21).contains("jwt-user/auth") && token!=null) {
			token = token.substring(7);
			String userName = jwtService.extractUserName(token);
			try {
				UserDetails userDetail = jwtUsrService.loadUserByUsername(userName);
				if(userDetail != null) {
					System.out.println("User Accepted .... continue filter chain");
						UsernamePasswordAuthenticationToken usrnamePwdAuth = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
						SecurityContextHolder.getContext().setAuthentication(authManager.authenticate(usrnamePwdAuth));
				}
			} catch (Exception e) {
				e.printStackTrace();
				ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(userName + " is not found || does not have access to hit the request");
			}
		}
		filterChain.doFilter(request, response);
	}
}
