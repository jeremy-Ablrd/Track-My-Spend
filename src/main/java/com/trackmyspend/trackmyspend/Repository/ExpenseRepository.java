package com.trackmyspend.trackmyspend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trackmyspend.trackmyspend.Entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long>{
    List<Expense> findByUserId(Long userId);
}
