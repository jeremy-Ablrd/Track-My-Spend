package com.trackmyspend.trackmyspend.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/dashboard")
    public ResponseEntity<String> adminDashboard() {
        return ResponseEntity.ok("Bienvenue sur le dashboard admin !");
    }

    // @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/user/profile")
    public ResponseEntity<String> userProfile() {
        return ResponseEntity.ok("Bienvenue sur votre profil !");
    }

}
