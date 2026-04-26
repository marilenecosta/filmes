package com.example.filmes.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class GeneroDAO {

    @Autowired
    private JdbcTemplate jdbc;

    public List<Genero> listar() {
        return jdbc.query(
            "SELECT * FROM genero ORDER BY id",
            (rs, rowNum) -> new Genero(
                rs.getInt("id"),
                rs.getString("nome")
            )
        );
    }

    public void salvar(Integer id, String nome) {
        if (id == null) {
            jdbc.update("INSERT INTO genero (nome) VALUES (?)", nome);
        } else {
            jdbc.update("UPDATE genero SET nome = ? WHERE id = ?", nome, id);
        }
    }

    public Genero buscarPorId(Integer id) {
        return jdbc.queryForObject(
            "SELECT * FROM genero WHERE id = ?",
            (rs, rowNum) -> new Genero(
                rs.getInt("id"),
                rs.getString("nome")
            ),
            id
        );
    }

    public void excluir(Integer id) {
        jdbc.update("DELETE FROM genero WHERE id = ?", id);
    }
}