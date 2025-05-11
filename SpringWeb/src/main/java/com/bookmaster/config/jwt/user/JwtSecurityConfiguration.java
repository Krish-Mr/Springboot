package com.bookmaster.config.jwt.user;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class JwtSecurityConfiguration {

	
	@Bean
	public AuthenticationManager authenticationManager(
			@Qualifier("inMemoryUserDetailsService") UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder);

	    return new ProviderManager(Collections.singletonList(authProvider));
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Primary
	public UserDetailsService inMemoryUserDetailsService() {
		UserDetailsService usrDetailsServ = new InMemoryUserDetailsManager();
		((InMemoryUserDetailsManager) usrDetailsServ).createUser(User.withUsername("admin1").password("admin@123").roles("admin").build());
		return usrDetailsServ;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(c->c.disable())
//			.httpBasic(Customizer.withDefaults())
			.formLogin(Customizer.withDefaults())
//			.rememberMe(Customizer.withDefaults())
//			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//			.authorizeHttpRequests(auth->auth.requestMatchers("jwt-user/**").authenticated())
//			.authorizeHttpRequests(auth->auth.requestMatchers("/**").permitAll())
			.build();
	}

	 @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Custom JWT Auth API Documentation Title").version("5.9"));
//                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
//                .components(new io.swagger.v3.oas.models.Components()
//                        .addSecuritySchemes("bearerAuth",
//                                new SecurityScheme()
//                                        .name("bearerAuth")
//                                        .type(SecurityScheme.Type.HTTP)
//                                        .scheme("bearer")
//                                        .bearerFormat("JWT")));
    }
}