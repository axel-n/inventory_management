package com.example.inventory_management.spring.auth;

import com.example.inventory_management.models.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class JWTUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	@Value("${jwt.expiration}")
	private String expirationTime;

	private long expirationTimeLong;

	@Value("${jwt.key}")
	private String key;

	private final SignatureAlgorithm ALGORITHM = SignatureAlgorithm.HS384;

	@PostConstruct
	private void init() {
		expirationTimeLong = Long.parseLong(expirationTime); //in second
	}

	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(key)
				.parseClaimsJws(token)
				.getBody();
	}

	public String getUsernameFromToken(String token) {
		return getAllClaimsFromToken(token).getSubject();
	}

	private Date getExpirationDateFromToken(String token) {
		return getAllClaimsFromToken(token).getExpiration();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	String generateToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", user.getId());
		claims.put("role", user.getRole());
		return doGenerateToken(claims, user.getEmail());
	}

	private String doGenerateToken(Map<String, Object> claims, String username) {
		final Date createdDate = new Date();
		final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(createdDate)
				.setExpiration(expirationDate)
				.signWith(ALGORITHM, key)  // чтобы часто не обновлять настройки postMan
				.compact();
	}

	public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	}

	public Claims getAllClaimsFromHeaders(HttpHeaders headers) {
		String token = Objects.requireNonNull(headers.getFirst(HttpHeaders.AUTHORIZATION)).substring(7);
		return getAllClaimsFromToken(token);
	}
}
