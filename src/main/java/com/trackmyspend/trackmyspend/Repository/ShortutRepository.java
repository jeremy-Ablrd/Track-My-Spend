package com.trackmyspend.trackmyspend.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trackmyspend.trackmyspend.Entity.Shortcut;

public interface ShortutRepository extends JpaRepository<Shortcut, Long> {

}
