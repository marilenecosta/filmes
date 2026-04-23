package com.example.filmes.model;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FilmeDAO {

    @Autowired
    private JdbcTemplate jdbc;

    public void inserir(String titulo, Integer ano, String diretor, Integer generoId) {
        String sql = "INSERT INTO filme (titulo, ano, diretor, genero_id) VALUES (?, ?, ?, ?)";
        jdbc.update(sql, titulo, ano, diretor, generoId);
    }

    public List<Map<String, Object>> listar() {
        String sql = "SELECT f.id, f.titulo, f.ano, f.diretor, g.nome as genero " +
                     "FROM filme f LEFT JOIN genero g ON f.genero_id = g.id";
        return jdbc.queryForList(sql);
    }

    public Map<String, Object> buscarPorId(UUID id) {
        
        String sql = "SELECT id, titulo, ano, diretor, genero_id FROM filme WHERE id = ?";
        return jdbc.queryForMap(sql, id);
    }

    public void atualizar(UUID id, String titulo, Integer ano, String diretor, Integer generoId) {
        String sql = "UPDATE filme SET titulo = ?, ano = ?, diretor = ?, genero_id = ? WHERE id = ?";
        jdbc.update(sql, titulo, ano, diretor, generoId, id);
    }

    public void excluir(UUID id) {
        jdbc.update("DELETE FROM filme WHERE id = ?", id);
    }
}