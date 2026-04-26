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
        List<Genero> listaGeneros = generoService.listarTodos(); 
        
        model.addAttribute("filmes", listaFilmes);
        model.addAttribute("generos", listaGeneros);
        
        // Garante que o formulário comece vazio (Modo Adicionar)
        if (!model.containsAttribute("filme")) {
            model.addAttribute("filme", null); 
        }
        
        return "index";
    }

    @PostMapping("/filme/salvar")
    public String salvar(@RequestParam(required = false) UUID id, // Adicionado ID para suportar atualização aqui também
                        @RequestParam String titulo,
                        @RequestParam(required = false) Integer ano,
                        @RequestParam(required = false) String diretor,
                        @RequestParam Integer generoId) {

        if (ano == null) {
            return "redirect:/gerenciar?erro=ano";
        }

        if (id == null) {
            filmeService.salvarFilme(titulo, ano, diretor, generoId);
        } else {
            filmeService.atualizarFilme(id, titulo, ano, diretor, generoId);
        }
        
        return "redirect:/gerenciar?sucesso";
    }

    @GetMapping("/filme/editar/{id}")
    public String editar(@PathVariable UUID id, Model model) {
        Map<String, Object> filme = filmeService.buscarPorId(id);
        model.addAttribute("filme", filme); // Envia o filme preenchido
        model.addAttribute("filmes", filmeService.listarFilmes());
        model.addAttribute("generos", generoService.listarTodos()); 
        return "index"; // Alterado para 'index' para editar na mesma tela
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
        model.addAttribute("generoObj", new Genero()); 
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
        model.addAttribute("generoObj", generoService.buscarPorId(id)); 
        return "generos";
    }
    
    @GetMapping("/genero/excluir/{id}")
    public String excluirGeneroDoSistema(@PathVariable Integer id) {
        try {
            generoService.excluir(id); 
            return "redirect:/generos?excluido";
        } catch (org.springframework.dao.DataIntegrityViolationException e) {            
            return "redirect:/generos?erro=em_uso";
        } catch (Exception e) {            
            return "redirect:/generos?erro=inesperado";
        }
    }
}