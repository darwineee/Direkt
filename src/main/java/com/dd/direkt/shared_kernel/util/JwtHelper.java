package com.dd.direkt.shared_kernel.util;

import com.dd.direkt.shared_kernel.domain.type.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtHelper {

    public static final String CLAIM_ROLES = "roles";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expr}")
    private long expr;

    public record UserCredentials(
            String token,
            String email,
            List<String> roles
    ) {}

    @SuppressWarnings("unchecked")
    public UserCredentials extractCredentials(String bearer) {
        if (bearer == null) return null;
        if (!bearer.startsWith("Bearer ")) return null;
        var token = bearer.substring(7);
        var email = extractClaims(token, Claims::getSubject);
        if (email == null) return null;
        var roles = (List<String>) extractClaims(token, claims -> claims.get(CLAIM_ROLES));
        if (roles.isEmpty()) return null;
        return new UserCredentials(token, email, roles);
    }

    public boolean validateToken(
            String token,
            String email,
            UserDetails user
    ) {
        if (!user.getUsername().equals(email)) return false;
        return extractClaims(token, Claims::getExpiration).after(new Date());
    }

    public String generateToken(String email, List<UserRole> role) {
        return Jwts.builder()
                .claims()
                .subject(email)
                .add(CLAIM_ROLES, role.stream().map(UserRole::name).toList())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expr))
                .and()
                .signWith(getSecretKey())
                .compact();
    }

    private <T> T extractClaims(String token, Function<Claims, T> select) {
        var claims = getClaims(token);
        return select.apply(claims);
    }

    private SecretKey getSecretKey() {
        var bytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(bytes);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
