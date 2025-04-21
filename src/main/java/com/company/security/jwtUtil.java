package com.company.security;

import com.company.user.Role;
import com.company.user.UserResp;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class jwtUtil {

    private static final int TOKEN_LIFETIME = 1000 * 3600 * 24; // 1-day
    private static final String SECRET_KEY = "veryLongSecretmazgillattayevlasharaaxmojonjinnijonsurbetbekkiydirhonuxlatdibekloxovdangasabekochkozjonduxovmashaynikmaydagapchishularnioqiganbolsangizgapyoqaniqsizmazgi";

    public static String encode(String username, Role role) {

        String token = Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_LIFETIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return  token;
    }

    public static UserResp decode(String token) {

        Claims body = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        String username = body.getSubject();
        String role = (String) body.get("role");

        return new UserResp(username, Role.valueOf(role));
    }
}
