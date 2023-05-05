package com.maximillian.blogapiwithsecurity.utils;

import com.maximillian.blogapiwithsecurity.Models.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.secretKey}")
    String secretKey;
    @Value("${application.security.jwt.expiration}")
    long jwtExpiration;
    public String extractUsername(String token){
        return extractClaims(token, Claims:: getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte [] byteKeys = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(byteKeys);
    }

    public boolean isTokenValid(
            String jwtToken, UserDetails userDetails) {
        String username = extractUsername(jwtToken);
        return (userDetails.getUsername().equals(username) && !isTokenExpired(jwtToken));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public String generateToken(UserDetails userdetails) {
        //collections to get the simple authorities
        Collection<? extends SimpleGrantedAuthority> authorities =
                (Collection<? extends SimpleGrantedAuthority>) userdetails.getAuthorities();

        //Initialize an array list to hold the authorities
        List<String> authorityName = new ArrayList<>();

        //Loop through and add the simple authorities to the list
        for(SimpleGrantedAuthority authority : authorities){
            authorityName.add(authority.getAuthority());
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", authorityName);
        return buildToken(claims, userdetails, jwtExpiration);
    }

    public String buildToken(
           Map<String, Object> extraClaims,
           UserDetails userDetails,
           long expiration
    ){

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setSubject(userDetails.getUsername())
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
