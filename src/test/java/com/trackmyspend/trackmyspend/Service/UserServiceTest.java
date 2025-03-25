package com.trackmyspend.trackmyspend.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.trackmyspend.trackmyspend.Entity.AppUser;
import com.trackmyspend.trackmyspend.Repository.UserRepository;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository; // Simulation du repository

    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private UserService userService; // Service à tester

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialisation des mocks
    }

    @Test
    public void testCreateUser() {
        // Créer un utilisateur fictif
        AppUser user = new AppUser();
        user.setUsername("TestUser");
        user.setEmail("testuser@example.com");
        user.setPassword("testpassword");

        // Simuler l'encodage du password
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Simuler le comportement du repository (quand on sauvegarde, il renvoie l'utilisateur)
        when(userRepository.save(any(AppUser.class))).thenReturn(user);

        // Exécuter la méthode du service
        AppUser createdUser = userService.createUser(user);

        // Vérifier que l'utilisateur retourné correspond bien
        assertEquals("TestUser", createdUser.getUsername());
        assertEquals("testuser@example.com", createdUser.getEmail());

        // Vérifier que la méthode save du repository a bien été appelée une fois
        verify(userRepository, times(1)).save(any(AppUser.class));
    }
}
