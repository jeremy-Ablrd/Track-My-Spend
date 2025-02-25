package com.trackmyspend.trackmyspend.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trackmyspend.trackmyspend.Entity.BudgetAlert;

public interface BudgetAlertRepository extends JpaRepository<BudgetAlert, Long> {
    List<BudgetAlert> findByUserId(Long userId);
}
