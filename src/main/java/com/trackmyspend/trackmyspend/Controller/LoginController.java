package com.trackmyspend.trackmyspend.Controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import com.trackmyspend.trackmyspend.Entity.AppUser;
import com.trackmyspend.trackmyspend.Repository.UserRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class LoginController {

 @Autowired
    private UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody AppUser user) {
        Optional<AppUser> dbUser = userRepository.findByEmail(user.getEmail());
    
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
        // Vérifie si l'utilisateur existe et si le mot de passe correspond
        if (dbUser.isPresent()) {
            if(encoder.matches(user.getPassword(), dbUser.get().getPassword())) {
                // Génère un JWT simple
                // @SuppressWarnings("deprecation")
                // String token = Jwts.builder()
                //         .subject(user.getUsername())
                //         .issuedAt(new Date())
                //         .expiration(new Date(System.currentTimeMillis() + 3600000)) // 1 heure
                //         .signWith(SignatureAlgorithm.HS256, secretKey)
                //         .compact();

                 // Récupérer la liste des rôles sous forme de chaîne de caractères
                List<String> roles = dbUser.get().getRoles().stream()
                    .map(Enum::name) // Convertit l'Enum Role en String
                    .collect(Collectors.toList());
                SecretKey key2 = Keys.hmacShaKeyFor(secretKey.getBytes());
                String token = Jwts.builder().
                    subject(dbUser.get().getEmail()).
                    claim("roles", roles).
                    issuedAt(new Date()).
                    expiration(new Date(System.currentTimeMillis() + 86400000)). // 24 heures
                    signWith(key2, Jwts.SIG.HS256).compact();
        
                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                response.put("user", dbUser.get().getEmail());

                return response;
            }
        } else {
            throw new RuntimeException("Invalid username or password");
        }
        return null;
    }

    // @PostMapping
    // public ResponseEntity<String> login(@RequestBody LoginDTO loginRequest) {

    //     // Vérifiez les informations d'identification
    //     if (loginRequest.getUsername().equals("admin") && loginRequest.getPassword().equals("admin")){
    //         String token = generateToken(loginRequest.getUsername());
    //         return ResponseEntity.status(200).body(token);
    //     }

    //     return ResponseEntity.status(401).build();
    // }

    // // Fonction pour générer un token JWT
    // private String generateToken(String username) {
    //     SecretKey encodingSignAndSecret = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    //     return Jwts.builder()
    //             .subject(username)  // Définir l'utilisateur dans le token
    //             .issuedAt(new Date())  // Date de création du token
    //             .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))  // Définir l'expiration du token
    //             .signWith(encodingSignAndSecret)  // Signer le token avec une clé secrète
    //             .compact();  // Générer le token sous forme de chaîne
    // }
}
