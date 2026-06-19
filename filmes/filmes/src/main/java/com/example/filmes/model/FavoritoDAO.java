package com.example.filmes.model;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Repository
public class FavoritoDAO {

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbc;

    @PostConstruct
    private void initialize() {
        if (dataSource != null) {
            jdbc = new JdbcTemplate(dataSource);
        }
    }

    public boolean jaEhFavorito(String usuarioId, String filmeId) {
        String sql = "SELECT COUNT(*) FROM favorito WHERE usuario_id = ?::uuid AND filme_id = ?::uuid";
        Integer count = jdbc.queryForObject(sql, Integer.class, usuarioId, filmeId);
        return count != null && count > 0;
    }

    public boolean favoritar(Favorito fav) {
        if (jaEhFavorito(fav.getUsuarioId(), fav.getFilmeId())) {
            return false;
        }

        String sql = "INSERT INTO favorito(usuario_id, filme_id, data_favoritado) VALUES (?::uuid, ?::uuid, ?)";
        Object[] obj = new Object[3];
        obj[0] = fav.getUsuarioId();
        obj[1] = fav.getFilmeId();
        obj[2] = java.sql.Date.valueOf(fav.getDataFavoritado());

        jdbc.update(sql, obj);
        return true;
    }

    public List<Map<String, Object>> buscarFavoritosDoUsuario(String usuarioId) {
        String sql = "SELECT f.*, fl.titulo, fl.ano, fl.diretor " +
                "FROM favorito f " +
                "INNER JOIN filme fl ON f.filme_id = fl.id " +
                "WHERE f.usuario_id = ?::uuid";
        return jdbc.queryForList(sql, usuarioId);
    }

    public void remover(String usuarioId, String filmeId) {
        String sql = "DELETE FROM favorito WHERE usuario_id = ?::uuid AND filme_id = ?::uuid";
        jdbc.update(sql, usuarioId, filmeId);
    }
}