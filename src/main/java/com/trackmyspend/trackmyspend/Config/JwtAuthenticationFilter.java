package com.trackmyspend.trackmyspend.Config;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secretKey; // Même clé que pour signer le token

    public JwtAuthenticationFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

    String authHeader = request.getHeader("Authorization");
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
    }

    String token = authHeader.substring(7); // Supprime "Bearer "

    Map<String, Object> userDetailsMap = extractUserDetails(token);

    String username = (String) userDetailsMap.get("username");
    
    List<String> roles = (List<String>) userDetailsMap.get("roles");

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (validateToken(token, userDetails)) {
            // Convertit les rôles en SimpleGrantedAuthority pour Spring Security
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }
    
    filterChain.doFilter(request, response);
}

    private Map<String, Object> extractUserDetails(String token) {
    SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
    Claims claims = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();

    Map<String, Object> userDetails = new HashMap<>();
    userDetails.put("username", claims.getSubject()); // Récupère le subject (email)
    userDetails.put("roles", claims.get("roles", List.class)); // Récupère la liste des rôles

    return userDetails;
}


    // #### old version ####
    // private String extractUsername(String token) {
    //     SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
    //     Claims claims = Jwts.parser()
    //         .verifyWith(key)
    //         .build()
    //         .parseSignedClaims(token)
    //         .getPayload();
    //     return claims.getSubject();
    // }

    private boolean validateToken(String token, UserDetails userDetails) {
        Map<String, Object> userDetailsMap = extractUserDetails(token);
        String extractedUsername = (String) userDetailsMap.get("username"); // Récupère le username
        return extractedUsername.equals(userDetails.getUsername()); // Vérifie si c'est bien le même utilisateur
    }
}
