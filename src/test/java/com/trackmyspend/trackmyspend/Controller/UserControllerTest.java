package com.trackmyspend.trackmyspend.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trackmyspend.trackmyspend.Entity.AppUser;
import com.trackmyspend.trackmyspend.Service.UserService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
 private MockMvc mockMvc;

    @Mock
    private UserService userService; // Simuler le service

    @InjectMocks
    private UserController userController; // Contrôleur à tester

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialisation des mocks
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build(); // Configuration MockMvc
    }

    @Test
    public void shouldCreateUser() throws Exception {
        // Créer un utilisateur fictif
        AppUser user = new AppUser();
        user.setUsername("TestUser");
        user.setEmail("testuser@example.com");
        user.setPassword("testpassword");

        // Simuler le comportement du service
        when(userService.createUser(any(AppUser.class))).thenReturn(user);

        // Envoyer une requête HTTP POST pour créer un utilisateur
        mockMvc.perform(post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user))) // Convertir l'objet en JSON
                .andExpect(status().isCreated()); // Vérifie que le statut est 201 Created
    }
}
