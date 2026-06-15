package com.example.filmes.model;

import java.time.LocalDate;

public class Favorito {
    private String usuarioId;
    private String filmeId;
    private LocalDate dataFavoritado;

    // Construtor vazio padrão
    public Favorito() {
        this.dataFavoritado = LocalDate.now();
    }

    // Construtor completo
    public Favorito(String usuarioId, String filmeId) {
        this.usuarioId = usuarioId;
        this.filmeId = filmeId;
        this.dataFavoritado = LocalDate.now(); // Grava o dia atual automaticamente
    }

    // Getters e Setters
    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getFilmeId() {
        return filmeId;
    }

    public void setFilmeId(String filmeId) {
        this.filmeId = filmeId;
    }

    public LocalDate getDataFavoritado() {
        return dataFavoritado;
    }

    public void setDataFavoritado(LocalDate dataFavoritado) {
        this.dataFavoritado = dataFavoritado;
    }
}