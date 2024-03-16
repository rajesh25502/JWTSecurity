package com.example.springJWT.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JWTUtils {
    private SecretKey Key;

    private static final long EXPIRATION_TIME=86400000; //or 24 hours

    public JWTUtils()
    {
        String secretString="983748774994ITU039R202983774HGRU398Y34YGR2826521T12E";
        byte[] keyBites= Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
        this.Key= new SecretKeySpec(keyBites,"HmacSHA256");
    }

    public String generateToken(UserDetails userDetails)
    {
        return Jwts.builder().subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }

    public String generateRefreshToken(HashMap<String,Object> claims,UserDetails userDetails)
    {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(Key)
                .compact();
    }

    public String extractUserName(String token)
    {
        return extractClaims(token, Claims::getSubject);
    }
    public <T>T extractClaims(String token, Function<Claims,T> claimsTFunction)
    {
        return claimsTFunction.apply(Jwts.parser().verifyWith(Key).build().parseSignedClaims(token).getPayload());
    }

    public boolean isTokenValid(String token, UserDetails userDetails)
    {
        final String userName=extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public  boolean isTokenExpired(String token)
    {
        return extractClaims(token,Claims::getExpiration).before(new Date());
    }

}
