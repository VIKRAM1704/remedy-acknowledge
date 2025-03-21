package com.hcltech.capstone.project.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.hcltech.capstone.project.entity.Login;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import static io.jsonwebtoken.Jwts.*;

@Component
public class JWTUtils implements Serializable {
	@Serial
	private static final long serialVersionUID = -2550185165626007488L;
	public static final long JWT_TOKEN_VALIDITY = 12L * 60 * 60 * 60;

	@Value("${jwt.secret}")
	public String secret;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String generateToken(Login login) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", login.getUsername());
		claims.put("password", login.getPassword());
		claims.put("role", login.getRole());

		try {
			return getAccessToken(claims, login.getUsername());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public String getAccessToken(Map<String, Object> claims, String userName) {
		try {
			return builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
					.signWith(SignatureAlgorithm.HS256, secret).compact();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public UsernamePasswordAuthenticationToken getAuthentication(final UserDetails userDetails) {
		final List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
	}

	public String extractUsername(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}
}