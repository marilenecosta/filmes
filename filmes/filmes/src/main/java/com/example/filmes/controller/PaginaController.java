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

    // --- FILMES ---

    @GetMapping("/gerenciar")
    public String gerenciar(Model model) {
        List<Map<String, Object>> listaFilmes = filmeService.listarFilmes();
        List<Genero> listaGeneros = generoService.listarTodos(); // ✅ corrigido
        
        model.addAttribute("filmes", listaFilmes);
        model.addAttribute("generos", listaGeneros);
        return "index";
    }

    @PostMapping("/filme/salvar")
    public String salvar(@RequestParam String titulo,
                        @RequestParam(required = false) Integer ano,
                        @RequestParam(required = false) String diretor,
                        @RequestParam Integer generoId) {

        if (ano == null) {
            return "redirect:/gerenciar?erro=ano";
        }

        filmeService.salvarFilme(titulo, ano, diretor, generoId);
        return "redirect:/gerenciar?sucesso";
    }

    @GetMapping("/filme/editar/{id}")
    public String editar(@PathVariable UUID id, Model model) {
        Map<String, Object> filme = filmeService.buscarPorId(id);
        model.addAttribute("filme", filme);
        model.addAttribute("generos", generoService.listarTodos()); // agora é List<Genero>
        return "editar"; 
    }

    @PostMapping("/filme/atualizar")
    public String atualizar(@RequestParam UUID id, 
                            @RequestParam String titulo,
                            @RequestParam(required = false) Integer ano,
                            @RequestParam(required = false) String diretor,
                            @RequestParam Integer generoId) {
        filmeService.atualizarFilme(id, titulo, ano, diretor, generoId);
        return "redirect:/gerenciar?sucesso";
    }

    @GetMapping("/filme/excluir/{id}")
    public String excluir(@PathVariable UUID id) {
        filmeService.excluirFilme(id);
        return "redirect:/gerenciar?excluido";
    }

    // --- GÊNEROS ---

    @GetMapping("/generos")
    public String listarPaginaGeneros(Model model) {
        model.addAttribute("generos", generoService.listarTodos());
        model.addAttribute("generoObj", new Genero()); // ✅ CORREÇÃO PRINCIPAL
        return "generos";
    }

    @PostMapping("/genero/salvar")
    public String salvarNovoGenero(@RequestParam(required = false) Integer id,
                                  @RequestParam String nome) {
        if (id == null || id == 0) {
            generoService.salvarNovo(nome);
        } else {
            generoService.atualizarExistente(id, nome);
        }
        return "redirect:/generos?sucesso";
    }

    @GetMapping("/genero/editar/{id}")
    public String editarGenero(@PathVariable Integer id, Model model) {
        model.addAttribute("generos", generoService.listarTodos());
        model.addAttribute("generoObj", generoService.buscarPorId(id)); // já está correto
        return "generos";
    }

    @GetMapping("/genero/excluir/{id}")
    public String excluirGeneroDoSistema(@PathVariable Integer id) {
        generoService.excluir(id); 
        return "redirect:/generos?excluido";
    }
}