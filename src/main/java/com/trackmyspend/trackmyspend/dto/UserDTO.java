package com.trackmyspend.trackmyspend.dto;
import java.util.List;
import java.util.Set;

import com.trackmyspend.trackmyspend.Entity.AppUser;
import com.trackmyspend.trackmyspend.Entity.Expense;
import com.trackmyspend.trackmyspend.Entity.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    // afficher uniquement les champs concernés, le password n'est pas afficher pour des raison de sécurité
    private String username;
    private String email;
    private Set<Role> role;
    private List<Expense> expenses;

    public UserDTO(AppUser p) {
        this.username = p.getUsername();
        this.email = p.getEmail();
        this.role = p.getRoles();
        this.expenses = p.getExpenses();
    }
}
