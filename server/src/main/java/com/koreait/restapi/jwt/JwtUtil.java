package com.koreait.restapi.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    // 토큰 발행 시 암호화하는 secret key
    private final String SECRET = "MySuperSecretKeyForJWTTokenWhichIsVerySecure12345";
    private final long EXPIRATION = 1000*60*60;  // 1시간
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // 토큰 만드는 메소드
    public String generateToken(String username, int userId) {
        return Jwts.builder().setSubject(username)
                .claim("id", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰 분해하는 메소드
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    public Integer getUserIdFromToken(String token) {
        return parseClaims(token).get("id", Integer.class);
    }

    public boolean isTokenValid(String token) {
        try{
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 클라이언트의 요청에 있는 bearer 토큰의 유효성을 검사하는 메소드
    public Integer getUserIdFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            String token = bearer.substring(7);
            return getUserIdFromToken(token);
        }
        throw new RuntimeException("토큰 에러!");
    }


} // class
