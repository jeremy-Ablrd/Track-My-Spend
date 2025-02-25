package com.trackmyspend.trackmyspend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trackmyspend.trackmyspend.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

}
