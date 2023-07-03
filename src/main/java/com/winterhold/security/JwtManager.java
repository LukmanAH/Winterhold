package com.winterhold.security;


// generator token
// verificator token
//

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtManager{

    private final String SECRET_KEY =  "liberate-tutume-ex-inferis-ad-astra-per-asperao";
    public String generateToken(String username, String secreetKey, String subject, String audience){
        JwtBuilder builder= Jwts.builder();
        builder.setSubject(subject)
                .setIssuer("http://localhost:7070")
                .setAudience(audience)
                .claim("username", username) //payload tambahan
                .signWith(SignatureAlgorithm.HS256, secreetKey);
        return  builder.compact();
    }

    private Claims getClainsFromToken(String token){
        JwtParser parser = Jwts.parser().setSigningKey(SECRET_KEY);
        Jws<Claims> signaturAndClaims = parser.parseClaimsJws(token);
        Claims claims = signaturAndClaims.getBody();
        return claims;
    }

    public String getUsernameByToken(String token){
        Claims claims = getClainsFromToken(token);
        return claims.get("username", String.class);
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        Claims claims = getClainsFromToken(token);
        String username = claims.get("username", String.class);
        Boolean isMatch = username.equals(userDetails.getUsername());
        return isMatch;
    }
}
