package com.example.filmes.model;

import java.util.Map;

public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String password;

    // Construtor vazio padrão
    public Usuario() {
    }

    // Construtor completo
    public Usuario(String id, String nome, String email, String password) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.password = password;
    }

    // Método conversor idêntico ao que o professor utiliza nas DAOs dele
    public static Usuario converter(Map<String, Object> registro) {
        Usuario user = new Usuario();
        user.setId(String.valueOf(registro.get("id")));
        user.setNome((String) registro.get("nome"));
        user.setEmail((String) registro.get("email"));
        user.setPassword((String) registro.get("password"));
        return user;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}