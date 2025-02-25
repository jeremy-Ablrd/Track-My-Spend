package com.trackmyspend.trackmyspend.dto;
import java.util.Set;

import com.trackmyspend.trackmyspend.Entity.AppUser;
import com.trackmyspend.trackmyspend.Entity.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    // private Long id;
    private String username;
    private String email;
    private Set<Role> role;

    public UserDTO(AppUser p) {
        this.username = p.getUsername();
        this.email = p.getEmail();
        this.role = p.getRoles();
    }
}
