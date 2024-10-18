package com.diginamic.demo.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

	public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";

	// Extrait le nom d'utilisateur du token JWT
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// Extrait la date d'expiration du token JWT
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	// Extrait une réclamation (claim) spécifique du token JWT
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	// Extrait toutes les réclamations du token
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	// Vérifie si le token est expiré
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// Valide le token JWT en comparant le nom d'utilisateur et l'état d'expiration
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	// Génère un token JWT pour un utilisateur
	public String generateToken(String username) { // Renommé selon les conventions Java
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, username);
	}

	// Crée le token avec les réclamations et le nom d'utilisateur
	private String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 heures d'expiration
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	// Récupère la clé de signature à partir du secret en Base64
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
