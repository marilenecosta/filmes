package com.example.filmes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.filmes.model.Usuario;
import com.example.filmes.model.UsuarioDAO;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
            Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "Nome ou senha inválidos.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String efetuarLogin(@RequestParam("username") String email,
            @RequestParam("password") String senha,
            HttpSession session) {

        Usuario usuario = usuarioDAO.buscarPorEmail(email);

        // Validação: se o usuário existir e a senha bater
        if (usuario != null && usuario.getPassword().equals(senha)) {
            session.setAttribute("usuarioLogado", usuario);
            return "redirect:/gerenciar";
        }

        return "redirect:/login?error=true";
    }

    @GetMapping("/logout")
    public String efetuarLogout(HttpSession session, RedirectAttributes attributes) {
        session.invalidate();
        attributes.addFlashAttribute("mensagem", "Você saiu da sua conta.");
        return "redirect:/gerenciar";
    }
}