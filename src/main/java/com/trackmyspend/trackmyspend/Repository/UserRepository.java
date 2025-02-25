package com.trackmyspend.trackmyspend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.trackmyspend.trackmyspend.Entity.AppUser;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long>{
    Optional<AppUser> findByEmail(String email);
}
