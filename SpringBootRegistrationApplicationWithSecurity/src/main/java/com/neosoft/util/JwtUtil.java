package com.neosoft.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	@Value("${app.secret}")
	private String secret;
	
	//validate username in token and database expDate
	public boolean validateToken(String token,String username) {
		String tokenUsername = getUsername(token);
		return (username.equals(tokenUsername)&& !isTokenExp(token));
	}
	
	//validate exp date
	public boolean isTokenExp(String token) {
		Date expDate=getExpDate(token);
		return expDate.before(new Date(System.currentTimeMillis()));
	}
	
	//read username
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}
	
	//read exp date
	public Date getExpDate(String token) {
		
		return getClaims(token).getExpiration();
	}
	
	//read claims
	public Claims getClaims(String token ) {
		return Jwts.parser()
				.setSigningKey(secret.getBytes())
				.parseClaimsJws(token)
				.getBody();
	}
	
	//generate token
	public String generateToken(String subject) {

		return Jwts.builder()
				.setSubject(subject)
				.setIssuer("shubham")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(15)))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
		
	}
}
