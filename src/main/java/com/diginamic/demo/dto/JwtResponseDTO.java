package com.diginamic.demo.dto;

import java.util.Set;

public class JwtResponseDTO {

    private String token;        // Le JWT généré
    private String type = "Bearer";  // Le type de token (généralement "Bearer")
    private String username;     // Le nom d'utilisateur
    private Set<String> roles;   // Les rôles associés à l'utilisateur (facultatif)

    // Constructeur par défaut
    public JwtResponseDTO() {
    }

    // Constructeur avec les paramètres token, username, et roles
    public JwtResponseDTO(String token, String username, Set<String> roles) {
        this.token = token;
        this.username = username;
        this.roles = roles;
    }

    // Getters et setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}