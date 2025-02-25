package com.trackmyspend.trackmyspend.Service;


import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trackmyspend.trackmyspend.Entity.AppUser;
import com.trackmyspend.trackmyspend.Entity.Role;
import com.trackmyspend.trackmyspend.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public AppUser createUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.USER)); // Transforme en Set<Role>
        return userRepository.save(user);
    }
}
