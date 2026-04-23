package com.example.filmes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.filmes.model.*; 
import java.util.UUID;
import java.util.Map;
import java.util.List;

@Controller
public class PaginaController {

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private GeneroService generoService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/gerenciar")
    public String gerenciar(Model model) {
        
        List<Map<String, Object>> listaFilmes = filmeService.listarFilmes();
        List<Map<String, Object>> listaGeneros = generoService.listarGeneros();
        
        model.addAttribute("filmes", listaFilmes);
        model.addAttribute("generos", listaGeneros);
        return "index";
    }

    @PostMapping("/filme/salvar")
    public String salvar(@RequestParam String titulo,
                         @RequestParam(required = false) Integer ano,
                         @RequestParam(required = false) String diretor,
                         @RequestParam Integer generoId) {
        filmeService.salvarFilme(titulo, ano, diretor, generoId);
        return "sucesso";
    }

    @GetMapping("/filme/editar/{id}")
    public String editar(@PathVariable UUID id, Model model) {
        Map<String, Object> filme = filmeService.buscarPorId(id);
        model.addAttribute("filme", filme);
        model.addAttribute("generos", generoService.listarGeneros());
        return "editar"; 
    }

    @PostMapping("/filme/atualizar")
    public String atualizar(@RequestParam UUID id, 
                            @RequestParam String titulo,
                            @RequestParam(required = false) Integer ano,
                            @RequestParam(required = false) String diretor,
                            @RequestParam Integer generoId) {
        filmeService.atualizarFilme(id, titulo, ano, diretor, generoId);
        return "redirect:/gerenciar";
    }

    @GetMapping("/filme/excluir/{id}")
    public String excluir(@PathVariable UUID id) {
        filmeService.excluirFilme(id);
        return "redirect:/gerenciar";
    }
}