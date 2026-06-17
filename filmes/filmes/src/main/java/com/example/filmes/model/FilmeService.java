package com.example.filmes.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FilmeService {

    @Autowired
    private FilmeDAO filmeDAO;

    // LISTAR TODOS OS FILMES
    public List<Map<String, Object>> listarFilmes() {
        return filmeDAO.listar();
    }

    // SALVAR FILME COM VALIDAÇÃO (REGRA DE NEGÓCIO)
    public void salvarFilme(String titulo, Integer ano, String diretor, Integer generoId, String urlImagem) {
        // Validação: Título deve ter ao menos 3 caracteres
        if (titulo == null || titulo.trim().length() < 3) {
            throw new IllegalArgumentException("titulo_curto");
        }
        
        // Validação: Ano não pode ser futuro
        int anoAtual = Year.now().getValue();
        if (ano == null || ano > anoAtual) {
            throw new IllegalArgumentException("ano_invalido");
        }

        filmeDAO.salvar(titulo, ano, diretor, generoId, urlImagem);
    }

    // ATUALIZAR FILME COM VALIDAÇÃO
    public void atualizarFilme(UUID id, String titulo, Integer ano, String diretor, Integer generoId, String urlImagem) {
        // Validação de título
        if (titulo == null || titulo.trim().length() < 3) {
            throw new IllegalArgumentException("titulo_curto");
        }
        
        // Validação de ano
        int anoAtual = Year.now().getValue();
        if (ano == null || ano > anoAtual) {
            throw new IllegalArgumentException("ano_invalido");
        }
        
        filmeDAO.atualizar(id, titulo, ano, diretor, generoId, urlImagem);
    }

    // BUSCAR POR ID
    public Map<String, Object> buscarPorId(UUID id) {
        return filmeDAO.buscarPorId(id);
    }

    // EXCLUIR FILME
    public void excluirFilme(UUID id) {
        filmeDAO.excluir(id);
    }

    // BUSCA INTELIGENTE (FILTROS)
    public List<Map<String, Object>> buscarFilmes(String titulo, Integer generoId) {
        if (titulo != null && !titulo.trim().isEmpty()) {
            return filmeDAO.pesquisarPorTitulo(titulo);
        } else if (generoId != null) {
            return filmeDAO.pesquisarPorGenero(generoId);
        } else {
            return filmeDAO.listar();
        }
    }
}