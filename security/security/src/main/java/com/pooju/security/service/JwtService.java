package com.pooju.security.service;

import com.pooju.security.UserDetails;
import com.pooju.security.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {


    private String secretKey=null;
    public String getSecretKey(){
        return secretKey="74c10ca9bdd0845da7a4034bd57708f15e88d8b77041088eca8a231e0bf5be58";
    };

    private SecretKey getKey(){

        byte[] decode = Decoders.BASE64.decode(getSecretKey());
        return Keys.hmacShaKeyFor(decode);
    }

    public String CreateJasonToken(User user) {
        Map<String, Objects> claims= new HashMap<>();

        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(user.getUsername())
                .issuer("pooju")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ 60*10*1000) )
                .and()
                .signWith(getKey())
                .compact();

    }

    public String extractUserName(String jwtToken) {

        return extractClaims(jwtToken, Claims::getSubject);
    }

    private <T> T extractClaims(String jwtToken, Function<Claims,T> claimResolver) {
        Claims claims= extractClaims(jwtToken);
        return claimResolver.apply(claims);
    }

    private Claims extractClaims(String jwtToken) {
        return
                Jwts
                        .parser()
                        .verifyWith(getKey())
                        .build()
                        .parseSignedClaims(jwtToken)
                        .getPayload();
    }

    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        String username=extractUserName(jwt);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(jwt));
    }

    private boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    private Date extractExpiration(String jwt) {
        return extractClaims(jwt,Claims::getExpiration);
    }
}
//2"43