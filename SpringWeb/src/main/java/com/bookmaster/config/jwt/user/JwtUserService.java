package com.bookmaster.config.jwt.user;

import java.util.Collections;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.models.examples.Example;

@Service
public class JwtUserService implements UserDetailsService {

	@Autowired
	JwtUserRepo userRepo;

	public JwtUserDetails findByUserId(String userId) throws Exception{
//		userRepo.findAll().stream().filter(e->e.getUserId().equals(userId)).findFirst();
		return userRepo.findByUserId(userId).orElseThrow( ()-> new Exception("user id is not found. login failed"));
	}
	
	public JwtUserDetails storeUser(JwtUserDetails user){
		System.out.println("Saving User Details: "+user.toString());
		return userRepo.saveAndFlush(user);
	}

	/**
	 * same as findByUserId but implementing UserDetailsService to check the user is exists
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		JwtUserDetails userJwt = userRepo.findByUserId(username).orElseThrow(()-> new UsernameNotFoundException(username + " username is not found in the database"));
		Objects.requireNonNull(userJwt);
		return new User(userJwt.getUserId(), userJwt.getPassword(),  Collections.singleton(new SimpleGrantedAuthority("admin")));
	}

}