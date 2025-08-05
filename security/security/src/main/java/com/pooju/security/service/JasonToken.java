package com.pooju.security.service;

import com.pooju.security.UserDetails;
import com.pooju.security.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;

@Service
public class JasonToken {


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

    public String extractUserName() {
        return "";
    }

    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        return true;
    }
}
//2"43