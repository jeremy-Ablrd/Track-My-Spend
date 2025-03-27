// package com.trackmyspend.trackmyspend.Controller;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// import java.util.Optional;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.trackmyspend.trackmyspend.Entity.AppUser;
// import com.trackmyspend.trackmyspend.Entity.Expense;
// import com.trackmyspend.trackmyspend.Repository.ExpenseRepository;
// import com.trackmyspend.trackmyspend.Repository.UserRepository;
// import com.trackmyspend.trackmyspend.dto.ExpenseDTO;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.security.core.context.SecurityContext;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.Authentication;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.springframework.http.MediaType;

// @WebMvcTest(ExpenseController.class)
// public class ExpenseControllerTest {

//     @Autowired
//     private MockMvc mockMvc; // Injection automatique de MockMvc

//     @Mock
//     private UserRepository userRepository;

//     @Mock
//     private ExpenseRepository expenseRepository;

//     @InjectMocks
//     private ExpenseController expenseController;

//     private AppUser user;
//     private Expense expense;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//         mockMvc = MockMvcBuilders.standaloneSetup(expenseController).build();

//         // Simuler un utilisateur
//         user = new AppUser();
//         user.setId(1L);
//         user.setUsername("TestUser");
//         user.setEmail("testuser@example.com");

//         // Simuler une dépense
//         expense = new Expense();
//         expense.setId(1L);
//         expense.setName("Achat");
//         expense.setAmount(50.0);
//         expense.setDescription("Courses");
//         expense.setUser(user);
//     }

//     @Test
//     void shouldAddExpense() throws Exception {
//         // Simuler l'authentification de l'utilisateur connecté
//         Authentication authentication = org.mockito.Mockito.mock(Authentication.class);
//         SecurityContext securityContext = org.mockito.Mockito.mock(SecurityContext.class);
//         org.mockito.Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
//         org.mockito.Mockito.when(authentication.getName()).thenReturn("testuser@example.com");
//         SecurityContextHolder.setContext(securityContext);

//         // Simuler la recherche de l'utilisateur en base
//         when(userRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));

//         // Simuler la sauvegarde de la dépense
//         when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

//         // Effectuer la requête et vérifier la réponse
//         mockMvc.perform(post("/expenses")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(new ObjectMapper().writeValueAsString(expense)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.name").value("Achat"))
//                 .andExpect(jsonPath("$.amount").value(50.0))
//                 .andExpect(jsonPath("$.description").value("Courses"))
//                 .andExpect(jsonPath("$.user.username").value("TestUser"));
//     }
// }
