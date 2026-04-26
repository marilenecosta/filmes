package com.example.filmes.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FilmeService {

    @Autowired
    private FilmeDAO filmeDAO;

    public List<Map<String, Object>> listarFilmes() {
        return filmeDAO.listar();
    }

    public void salvarFilme(String titulo, Integer ano, String diretor, Integer generoId) {
        filmeDAO.salvar(titulo, ano, diretor, generoId);
    }

    public Map<String, Object> buscarPorId(UUID id) {
        return filmeDAO.buscarPorId(id);
    }

    public void atualizarFilme(UUID id, String titulo, Integer ano, String diretor, Integer generoId) {
        filmeDAO.atualizar(id, titulo, ano, diretor, generoId);
    }

    public void excluirFilme(UUID id) {
        filmeDAO.excluir(id);
    }
}