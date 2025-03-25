package com.trackmyspend.trackmyspend.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.trackmyspend.trackmyspend.Entity.AppUser;
import com.trackmyspend.trackmyspend.Entity.Expense;
import com.trackmyspend.trackmyspend.Repository.ExpenseRepository;
import com.trackmyspend.trackmyspend.Repository.UserRepository;
import com.trackmyspend.trackmyspend.dto.ExpenseDTO;
import com.trackmyspend.trackmyspend.dto.UserDTO;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;

    @PostMapping
    public ResponseEntity<ExpenseDTO> addUserToExpense(@RequestBody Expense expense) {
        // Récupérer l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Récupère l'email de l'utilisateur connecté

        // recherche de l'utilisateur dans la base de données
        AppUser user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // assignée l'utilisateur dans la base de données
        expense.setUser(user);

        // Sauvegarder l'entité en base
        Expense savedExpense = expenseRepository.save(expense);

        //convertion en DTO    
        UserDTO userDTO = new UserDTO(user);
        ExpenseDTO expenceDTO = new ExpenseDTO(savedExpense.getId(), savedExpense.getName(), savedExpense.getAmount(), savedExpense.getDescription(), savedExpense.getDate(), userDTO);
        
        return ResponseEntity.ok(expenceDTO);
    }

    @GetMapping
    public List<ExpenseDTO> getExpense() {
    List<Expense> expenses = expenseRepository.findAll();
    
    // Transformer chaque Expense en ExpenseDTO et stocker dans une liste
    List<ExpenseDTO> expenseDTOList = expenses.stream()
        .map(expense -> new ExpenseDTO(
            expense.getId(),
            expense.getName(),
            expense.getAmount(),
            expense.getDescription(),
            expense.getDate(),
            new UserDTO(expense.getUser()) // Transformer l'utilisateur en DTO
        ))
        .collect(Collectors.toList());

        return expenseDTOList;
    }

}
