package com.bank.ledgerapi;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_users")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String cpf;

    @Column(unique = true, nullable = false)
    private String email;

    // ---- CONSTRUTORES ----
    public User() {

    }

    public User(String name, String cpf, String email) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
    }

    // ---- GETTERS E SETTERS ----

    public UUID getId() { return id;}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCpf() { return cpf; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
