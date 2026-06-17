package com.example.filmes.model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FilmeDAO {

    @Autowired
    private JdbcTemplate jdbc;

    public List<Map<String, Object>> listar() {
        String sql = "SELECT f.*, g.nome AS genero_nome FROM filme f " +
                     "LEFT JOIN genero g ON f.genero_id = g.id " +
                     "ORDER BY f.titulo ASC";
        return jdbc.queryForList(sql);
    }

    public List<Map<String, Object>> pesquisarPorTitulo(String titulo) {
        String sql = "SELECT f.*, g.nome AS genero_nome FROM filme f " +
                     "LEFT JOIN genero g ON f.genero_id = g.id " +
                     "WHERE UPPER(f.titulo) LIKE UPPER(?) ORDER BY f.titulo ASC";
        return jdbc.queryForList(sql, "%" + titulo + "%");
    }

    // Método SALVAR atualizado
    public void salvar(String titulo, Integer ano, String diretor, Integer generoId, String urlImagem) {
        String sql = "INSERT INTO filme (id, titulo, ano, diretor, genero_id, url_imagem) VALUES (?, ?, ?, ?, ?, ?)";
        jdbc.update(sql, UUID.randomUUID(), titulo, ano, diretor, generoId, urlImagem);
    }

    public Map<String, Object> buscarPorId(UUID id) {
        String sql = "SELECT * FROM filme WHERE id = ?";
        return jdbc.queryForMap(sql, id);
    }

    // Método ATUALIZAR atualizado
    public void atualizar(UUID id, String titulo, Integer ano, String diretor, Integer generoId, String urlImagem) {
        String sql = "UPDATE filme SET titulo = ?, ano = ?, diretor = ?, genero_id = ?, url_imagem = ? WHERE id = ?";
        jdbc.update(sql, titulo, ano, diretor, generoId, urlImagem, id);
    }

    public void excluir(UUID id) {
        String sql = "DELETE FROM filme WHERE id = ?";
        jdbc.update(sql, id);
    }

    public List<Map<String, Object>> pesquisarPorGenero(Integer generoId) {
        String sql = "SELECT f.*, g.nome AS genero_nome FROM filme f " +
                     "LEFT JOIN genero g ON f.genero_id = g.id " +
                     "WHERE f.genero_id = ? ORDER BY f.titulo ASC";
        return jdbc.queryForList(sql, generoId);
    }
}