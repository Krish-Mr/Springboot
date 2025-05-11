package com.bookmaster.config.jwt.user;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import org.springframework.boot.web.embedded.jetty.ConfigurableJettyWebServerFactory;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	final String key = "this is secret key for learnings JWT authentication";
	private final String secret_key = Base64.getEncoder().encodeToString(key.getBytes());
	private final long expirationTime = 20_000; //20 secs

	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignInKey(secret_key))
				.build()
				.parseClaimsJws(token).getBody();
	}
	
	public String extractUserName(String token) {
		return extractClaims(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaims(token, Claims::getExpiration);
	}
	
	public <T> T extractClaims(String token, Function<Claims, T> claimResolver) {
		Claims extClaims = extractAllClaims(token);
		return claimResolver.apply(extClaims);
	}
	
	private boolean isTokenExpire(String token) {
		return extractExpiration(token).before(new Date());
	}

	public boolean validateToken(String token, String user) {
		return (extractUserName(token).equals(user) && !isTokenExpire(token));
	}

	public String generateToken(String user) {
		return Jwts.builder()
//		.setClaims(new HashMap<String, String>())
		.setSubject(user)
		.setIssuedAt(new Date())
		.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5 ))  // 1 min
		.signWith(getSignInKey(secret_key), SignatureAlgorithm.HS256)
		.compact();
	}

	public static Key getSignInKey(String secret_key) {
		byte[] decode = Decoders.BASE64.decode(secret_key);
		return Keys.hmacShaKeyFor(decode);
	}


	public static void main(String[] args) throws InterruptedException {
		String securet_key = "gokulakrishnan B it my secret key";
		String encode_secret_key = Base64.getEncoder().encodeToString(securet_key.getBytes());
//		byte[] encode = Base64.getEncoder().encode(securet_key.getBytes());
		System.out.println(encode_secret_key);

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("1Id01", 20);
		map.put("2Id01", 20);

		Instant now = Instant.now();
		String jwtCompact = Jwts.builder()
			.addClaims(map)
			.setSubject("gokul")
			.setAudience("youtube")
			.setIssuedAt(Date.from(now))
			.setExpiration(Date.from(now.plus(1, ChronoUnit.MINUTES)))
			.signWith(getSignInKey(encode_secret_key), SignatureAlgorithm.HS256)
			.compact();

		System.out.println(jwtCompact);	
		Jwt parse = Jwts.parserBuilder()
			.requireAudience("youtube")
			.setSigningKey(getSignInKey(encode_secret_key))
			.build()
			.parse(jwtCompact);
		System.out.println(parse);
		System.out.println(parse.getHeader());
		System.out.println(parse.getBody());

		Jws<Claims> parseClaimsJws = Jwts.parserBuilder().setSigningKey(getSignInKey(encode_secret_key)).build().parseClaimsJws(jwtCompact);
		System.out.println("JWS claims: subject:   "+parseClaimsJws.getBody().getSubject());
		System.out.println("JWS claims: signature: "+ parseClaimsJws.getSignature());
	}
}
