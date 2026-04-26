package com.example.filmes.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GeneroService {

    @Autowired
    private GeneroDAO dao;

    public List<Genero> listarTodos() {
        return dao.listar();
    }

    public void salvarNovo(String nome) {
        dao.salvar(null, nome);
    }

    public void atualizarExistente(Integer id, String nome) {
        dao.salvar(id, nome);
    }

    public Genero buscarPorId(Integer id) {
        return dao.buscarPorId(id);
    }

    public void excluir(Integer id) {
        dao.excluir(id);
    }
}