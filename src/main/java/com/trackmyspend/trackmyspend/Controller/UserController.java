package com.trackmyspend.trackmyspend.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trackmyspend.trackmyspend.Entity.AppUser;
import com.trackmyspend.trackmyspend.Service.UserService;
import com.trackmyspend.trackmyspend.dto.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService UserService;

    @PostMapping("/create")
    public UserDTO createUser(@RequestBody AppUser user) {
        user = UserService.createUser(user);
        return new UserDTO(user);
    }
    
}
