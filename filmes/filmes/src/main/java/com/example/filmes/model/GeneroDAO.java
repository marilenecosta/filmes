package com.example.filmes.model;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GeneroDAO {

    @Autowired
    private JdbcTemplate jdbc;

    public List<Map<String, Object>> listar() {
        String sql = "SELECT * FROM genero";
        return jdbc.queryForList(sql);
    }
}