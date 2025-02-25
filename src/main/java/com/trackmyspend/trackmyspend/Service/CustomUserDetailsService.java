package com.trackmyspend.trackmyspend.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.trackmyspend.trackmyspend.Entity.AppUser;
import com.trackmyspend.trackmyspend.Repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final AppUser user = userRepository.findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));

    // Convertit les rôles en SimpleGrantedAuthority
    List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
            .collect(Collectors.toList());

    return new User(user.getEmail(), user.getPassword(), authorities);
}

    // ###### Old Version ######
    // @Override
    // public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    //     // Recherche un utilisateur par son nom d'utilisateur
    //     final AppUser user = userRepository.findByEmail(username)
    //             .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));

    //     return User.builder()
    //             .username(user.getEmail())
    //             .password(user.getPassword()) // Mot de passe déjà encodé
    //             .roles(user.getRoles().name()) // Convertit l'Enum Role en String
    //             .build();
    // }
}
