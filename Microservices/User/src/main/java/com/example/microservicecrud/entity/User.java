package com.example.microservicecrud.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String email;
    private String role;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate registrationDate;

    private String password;
    private String phone;
    private String numeroDeContrat;
    private String typeDeContrat;

    public User() {}

    public User(String nom, String prenom, String email, String role, LocalDate registrationDate,
                String password, String phone, String numeroDeContrat, String typeDeContrat) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.role = role;
        this.registrationDate = registrationDate;
        this.password = password;
        this.phone = phone;
        this.numeroDeContrat = numeroDeContrat;
        this.typeDeContrat = typeDeContrat;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNumeroDeContrat() {
        return numeroDeContrat;
    }

    public void setNumeroDeContrat(String numeroDeContrat) {
        this.numeroDeContrat = numeroDeContrat;
    }

    public String getTypeDeContrat() {
        return typeDeContrat;
    }

    public void setTypeDeContrat(String typeDeContrat) {
        this.typeDeContrat = typeDeContrat;
    }
}
