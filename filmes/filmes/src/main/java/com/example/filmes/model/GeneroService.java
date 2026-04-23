package com.example.filmes.model; // Ajustado para a sua pasta

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class GeneroService {

    @Autowired
    private GeneroDAO generoDAO;

    public List<Map<String, Object>> listarGeneros() {
        return generoDAO.listar(); 
    }
}