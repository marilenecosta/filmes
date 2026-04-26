package com.example.filmes.model;

import java.util.List;
import java.util.Map;
import java.util.UUID; // Importante estar aqui
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FilmeDAO {

    @Autowired
    private JdbcTemplate jdbc;

    public List<Map<String, Object>> listar() {
        String sql = "SELECT f.*, g.nome AS genero_nome FROM filme f " +
                     "LEFT JOIN genero g ON f.genero_id = g.id";
        return jdbc.queryForList(sql);
    }

    public void salvar(String titulo, Integer ano, String diretor, Integer generoId) {
        String sql = "INSERT INTO filme (id, titulo, ano, diretor, genero_id) VALUES (?, ?, ?, ?, ?)";
        jdbc.update(sql, UUID.randomUUID(), titulo, ano, diretor, generoId);
    }

    public Map<String, Object> buscarPorId(UUID id) {
        String sql = "SELECT * FROM filme WHERE id = ?";
        return jdbc.queryForMap(sql, id);
    }

    public void atualizar(UUID id, String titulo, Integer ano, String diretor, Integer generoId) {
        String sql = "UPDATE filme SET titulo = ?, ano = ?, diretor = ?, genero_id = ? WHERE id = ?";
        jdbc.update(sql, titulo, ano, diretor, generoId, id);
    }

    public void excluir(UUID id) {
        String sql = "DELETE FROM filme WHERE id = ?";
        jdbc.update(sql, id);
    }
}