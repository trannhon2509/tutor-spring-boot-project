package com.project.tutor.secutiry;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtProvider {
    // SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SERECT_KEY.getBytes());
    private final static String SERECT = "sjbdfkghsdgfjkhsdfgsdfgsdfgsdfgsdfgsdfgskdfhgasdfasdfasdf";
//    public String generateToken(Authentication auth) {
//        String jwt = Jwts.builder()
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(new Date().getTime() + 846000000))
//                .claim("username", auth.getName())
//                .claim("authorities", auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                .signWith(key).compact();
//        return jwt;
//    }
//
//    public String getUsernameFromToken(String token) {
//        if (token != null && token.startsWith("Bearer ")) {
//            token = token.substring(7);
//            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
//            String username = String.valueOf(claims.get("username"));
//            return username;
//        }
//        return null;
//    }
public String generateToken(String username) {
    Map<String, Objects> claims = new HashMap<>();
    return createToken(claims, username);
}
public String createToken(Map<String, Objects> claims, String username) {
    return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
            .signWith(SignatureAlgorithm.HS256, getSignKey())
            .compact();
}

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SERECT);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(SERECT).build().parseClaimsJws(token).getBody();
    }
    public <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        System.out.println("claims : " + claims);
        return claimsTFunction.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }
    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}
