package com.example.webshopapi.user;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50, unique = true)
    private String name;
}
