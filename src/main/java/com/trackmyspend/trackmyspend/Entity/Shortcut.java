package com.trackmyspend.trackmyspend.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Shortcut {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Ex: "Café du matin"

    private double amount; // Ex: 2.50€

    @Column(nullable = true)
    private String category; // Ex: "Alimentation" (peut être NULL)

    @Column(length = 255)
    private String description; // Ex: "Dépense quotidienne pour le café du matin"

    @Column(nullable = true, length = 100)
    private String logoName; // Identifiant du logo côté mobile (ex: "netflix", "spotify")

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user; // Lien avec l'utilisateur

}
