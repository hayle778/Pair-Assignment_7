package com.meritamerica.assignment6.Resource;
import com.fasterxml.classmate.AnnotationOverrides;
import org.springframework.data.annotation.Id;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil<Claims> {

    private String SECRET_KEY = "secret";
    private Object SignatureAlgorithm;
    private AnnotationOverrides Jwts;
    private String Username;
    private String Password;

    @ManyToOne
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    public String extractUsername(String token) {
        return extractUsername(token);
    }

    //public Date extractExpiration(String token) {
      //  return extractExpiration(token);
   // }

    public String extractPassword(String token){
        return extractUsername(token);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims();
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims() {
   return extractClaim();
    }
    private Claims extractClaim() {
    return extractClaim();
    }
    private Claims extractAllClaims(String token) {
     // return Jwts.notifyAll().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return  extractAllClaims(token);
    }
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }
    private String createToken(Map<String, Object> claims, String subject) {

 //return Jwts.builder().add().setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
       //   .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
     //.signWith(SignatureAlgorithm, SECRET_KEY).compact();
        return createToken(claims,subject);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()));
    }
}

