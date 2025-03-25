package com.trackmyspend.trackmyspend.dto;

import java.time.LocalDateTime;

import com.trackmyspend.trackmyspend.Entity.Expense;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExpenseDTO {
    private Long id;
    private String name;
    private double amount;
    private String description;
    private LocalDateTime date;
    private UserDTO user;



    public ExpenseDTO(Long id, String name, double amount, String description, LocalDateTime date, UserDTO user) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.user = user;
    }
}
