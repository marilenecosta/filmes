package com.example.filmes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.filmes.model.*;
import jakarta.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class PaginaController {

    @Autowired private FilmeService filmeService;
    @Autowired private GeneroService generoService;
    @Autowired private FavoritoDAO favoritoDAO; 

    private void adicionarContadorFavoritos(Model model, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado != null) {
            int total = favoritoDAO.buscarFavoritosDoUsuario(usuarioLogado.getId()).size();
            model.addAttribute("totalFavoritos", total);
        }
    }

    @GetMapping("/")
    public String home() { return "home"; }

    @GetMapping("/gerenciar")
    public String gerenciar(@RequestParam(required = false) String titulo,
                            @RequestParam(required = false) Integer generoId,
                            Model model, HttpSession session) {
        
        adicionarContadorFavoritos(model, session);
        model.addAttribute("filmes", filmeService.buscarFilmes(titulo, generoId));
        model.addAttribute("generos", generoService.listarTodos());
        
        if (!model.containsAttribute("filme")) {
            model.addAttribute("filme", null); 
        }
        return "index";
    }

    @PostMapping("/filme/salvar")
    public String salvar(@RequestParam(required = false) UUID id,
                         @RequestParam String titulo,
                         @RequestParam(required = false) Integer ano,
                         @RequestParam(required = false) String diretor,
                         @RequestParam Integer generoId,
                         @RequestParam(required = false) String urlImagem,
                         RedirectAttributes redirectAttributes) {
        
        try {
            if (id == null) {
                filmeService.salvarFilme(titulo, ano, diretor, generoId, urlImagem);
            } else {
                filmeService.atualizarFilme(id, titulo, ano, diretor, generoId, urlImagem);
            }
            redirectAttributes.addFlashAttribute("sucesso", "Operação realizada com sucesso!");
            return "redirect:/gerenciar";
            
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/gerenciar";
        }
    }

    @GetMapping("/filme/editar/{id}")
    public String editar(@PathVariable UUID id, Model model, HttpSession session) {
        adicionarContadorFavoritos(model, session);
        model.addAttribute("filme", filmeService.buscarPorId(id));
        model.addAttribute("filmes", filmeService.listarFilmes());
        model.addAttribute("generos", generoService.listarTodos()); 
        return "index";
    }

    @PostMapping("/filme/atualizar")
    public String atualizar(@RequestParam UUID id, 
                            @RequestParam String titulo,
                            @RequestParam(required = false) Integer ano,
                            @RequestParam(required = false) String diretor,
                            @RequestParam Integer generoId,
                            @RequestParam(required = false) String urlImagem,
                            RedirectAttributes redirectAttributes) {
        try {
            filmeService.atualizarFilme(id, titulo, ano, diretor, generoId, urlImagem);
            redirectAttributes.addFlashAttribute("sucesso", "Filme atualizado com sucesso!");
            return "redirect:/gerenciar";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/filme/editar/" + id;
        }
    }

    @GetMapping("/filme/excluir/{id}")
    public String excluir(@PathVariable UUID id, RedirectAttributes redirectAttributes) {
        filmeService.excluirFilme(id);
        redirectAttributes.addFlashAttribute("sucesso", "Filme excluído!");
        return "redirect:/gerenciar";
    }

    // --- GÊNEROS ---
    @GetMapping("/generos")
    public String listarPaginaGeneros(Model model) {
        model.addAttribute("generos", generoService.listarTodos());
        model.addAttribute("generoObj", new Genero()); 
        return "generos";
    }

    @PostMapping("/genero/salvar")
    public String salvarNovoGenero(@RequestParam(required = false) Integer id, @RequestParam String nome) {
        if (id == null || id == 0) generoService.salvarNovo(nome);
        else generoService.atualizarExistente(id, nome);
        return "redirect:/generos?sucesso";
    }

    @GetMapping("/genero/editar/{id}")
    public String editarGenero(@PathVariable Integer id, Model model) {
        model.addAttribute("generos", generoService.listarTodos());
        model.addAttribute("generoObj", generoService.buscarPorId(id)); 
        return "generos";
    }
    
    @GetMapping("/genero/excluir/{id}")
    public String excluirGeneroDoSistema(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            generoService.excluir(id); 
            return "redirect:/generos?excluido";
        } catch (org.springframework.dao.DataIntegrityViolationException e) {            
            ra.addFlashAttribute("erro", "Não é possível excluir: gênero em uso.");
            return "redirect:/generos";
        } catch (Exception e) {            
            return "redirect:/generos?erro=inesperado";
        }
    }
}