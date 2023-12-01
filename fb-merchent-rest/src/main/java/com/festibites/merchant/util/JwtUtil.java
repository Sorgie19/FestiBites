package com.festibites.merchant.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.festibites.merchant.model.user.User;
import com.festibites.merchant.service.user.UserService;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;



@Service
public class JwtUtil {
	
	@Autowired
	UserService userService;

    private String SECRET_KEY = "55d09386169f2368800f9f55ef780d1259069a5f13daa7e434fe2ff070cc2eb8"; // Replace with your secret key

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    	if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " to get the actual JWT
        }
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        
        UserDetails userDetails = userService.loadUserByUsername(username);
        
        // Retrieve the roles (authorities) and add them to the claims
        String roles = userDetails.getAuthorities().stream()
                        .map(grantedAuthority -> grantedAuthority.getAuthority())
                        .collect(Collectors.joining(","));  // Join roles if multiple
        
        String userDetailsUserName = userDetails.getUsername();

        claims.put("roles", roles); // Add roles to the claims
        claims.put("username", userDetailsUserName);
        
        return createToken(claims, userDetailsUserName);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 Hours validity
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
    
    public String getUsernameFromToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " to get the actual JWT
        }
        Claims claims = Jwts.parser()
                            .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                            .parseClaimsJws(token)
                            .getBody();
        return claims.getSubject();
    }

}
