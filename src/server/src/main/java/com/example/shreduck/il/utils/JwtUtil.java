package com.example.shreduck.il.utils;

import com.example.shreduck.dl.entities.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
public class JwtUtil {

    private final JwtBuilder builder;
    private final JwtParser parser;

    public JwtUtil() {
        final byte[] secret = "yabadabadoooooooooooooooooooooooooooooo".getBytes();
        final SecretKey secretKey = new SecretKeySpec(secret, "HmacSHA256");
        this.builder = Jwts.builder().signWith(secretKey);
        this.parser = Jwts.parser().verifyWith(secretKey).build();
    }

    private Claims getClaims(String token) {
        return this.parser.parseSignedClaims(token).getPayload();
    }

    public String getUsername(String token) {
        return this.getClaims(token).getSubject();
    }

    public String generateToken(Member member) {
        final long expireAfter = 96 * 60 * 60 * 1000L;
        return this.builder
                .subject(member.getUsername())
                .claim("id", member.getId())
                .claim("role", member.getMemberRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expireAfter))
                .compact();
    }

    public boolean isValid(String token) {
        Claims claims = getClaims(token);
        Date now = new Date();
        return now.after(claims.getIssuedAt()) && now.before(claims.getExpiration());
    }

}
