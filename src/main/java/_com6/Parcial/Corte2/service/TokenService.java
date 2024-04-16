package _com6.Parcial.Corte2.service;

import _com6.Parcial.Corte2.model.UserAccount;
import _com6.Parcial.Corte2.security.model.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class TokenService {
    private final JwtProperties jwtProperties;

    @Autowired
    public TokenService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    private Claims getAllClaims(String token){
        JwtParser parser = Jwts.parser()
                .verifyWith(getSecretKey())
                .build();
        return parser.parseSignedClaims(token).getPayload();
    }

    public String generate(UserDetails userDetails, Date expirationDate, Map<String, ?> additionalClaims){
        return Jwts.builder()
                .claims()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expirationDate)
                .add(additionalClaims)
                .and()
                .signWith(getSecretKey())
                .compact();
    }

    public String extractEmail(String token){
        return getAllClaims(token).getSubject();
    }

    public Long extractId(String token){
        return getAllClaims(token).get("userId", Long.class);
    }

    public boolean isAdministrator(String token){
        return getAllClaims(token).get("isAdmin", Boolean.class);
    }

    public void hasEnoughPermissions(HttpServletRequest request){
        String token = extractToken(request);
        if (!isAdministrator(token))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You do not have permission to make this action!");
    }

    public Boolean doesNotContainBearer(String authHeader) {
        return authHeader == null || !authHeader.startsWith("Bearer ");
    }

    public String extractToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        return authHeader.substring(7);
    }

    public Boolean isExpired(String token){
        return getAllClaims(token).getExpiration().before(new Date(System.currentTimeMillis()));
    }

    public Boolean isValid(String token, UserDetails userDetails){
        String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) && !isExpired(token);
    }

    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(
                jwtProperties.key().getBytes()
        );
    }

}
