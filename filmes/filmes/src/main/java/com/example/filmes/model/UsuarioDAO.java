package com.example.filmes.model;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Repository
public class UsuarioDAO {

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbc;

    @PostConstruct
    private void initialize() {
        if (dataSource != null) {
            jdbc = new JdbcTemplate(dataSource);
        }
    }

    // Busca o usuário pelo e-mail para fazer a validação do Login
    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        List<Map<String, Object>> registros = jdbc.queryForList(sql, email);

        if (registros == null || registros.isEmpty()) {
            return null;
        }

        return Usuario.converter(registros.get(0));
    }
}