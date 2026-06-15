package com.example.filmes.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.filmes.model.Favorito;
import com.example.filmes.model.FavoritoDAO;
import com.example.filmes.model.Usuario;

import jakarta.servlet.http.HttpSession;

@Controller
public class FavoritoController {

    @Autowired
    private FavoritoDAO favoritoDAO;

    @GetMapping("/favoritos")
    public String listarFavoritos(HttpSession session, Model model) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) return "redirect:/login";

        // Proteção contra Null: se o DAO retornar null, enviamos uma lista vazia
        List<Map<String, Object>> lista = favoritoDAO.buscarFavoritosDoUsuario(usuarioLogado.getId());
        model.addAttribute("favoritos", lista != null ? lista : new ArrayList<>());
        
        return "favoritos";
    }

    @PostMapping("/filme/{fid}/favoritar")
    public String favoritarFilme(@PathVariable("fid") String filmeId, HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) return "redirect:/login";
        
        Favorito favorito = new Favorito(usuarioLogado.getId(), filmeId);
        if (favoritoDAO.favoritar(favorito)) {
            redirectAttributes.addFlashAttribute("mensagemSucesso", "🎬 Filme adicionado aos favoritos!");
        } else {
            redirectAttributes.addFlashAttribute("mensagemErro", "⚠️ Este filme já está nos seus favoritos!");
        }
        return "redirect:/gerenciar"; 
    }

    @PostMapping("/filme/{fid}/remover-favorito")
    public String removerFavorito(@PathVariable("fid") String filmeId, HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado == null) return "redirect:/login";

        favoritoDAO.remover(usuarioLogado.getId(), filmeId);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "✅ Filme removido dos favoritos!");
        return "redirect:/favoritos";
    }
}