package com.vivaimoveis.imobiliaria.controller;

import com.vivaimoveis.imobiliaria.core.entity.Imovel;
import com.vivaimoveis.imobiliaria.core.repository.ImovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Random;

@Controller
public class HomeController {

    @Autowired
    private ImovelRepository imovelRepository;

    @GetMapping("/") // Mapeia a URL raiz
    public String index(Model model) {
        List<Imovel> imoveis = imovelRepository.findAll();

        if (imoveis != null && !imoveis.isEmpty()) {
            Random rand = new Random();
            Imovel imovelAleatorio = imoveis.get(rand.nextInt(imoveis.size()));
            model.addAttribute("imovelAleatorio", imovelAleatorio); // Passa o imóvel aleatório para o template
        } else {
            model.addAttribute("imovelAleatorio", null);
        }

        return "index"; // Retorna a página index.html
    }
    @GetMapping("/sobre") // Mapeia a URL raiz
    public String sobre() {
        return "sobre"; // Retorna a página sobre.html
    }

    @GetMapping("/contato") // Mapeia a URL raiz
    public String contato() {
        return "contato"; // Retorna a página contato.html
    }

    @GetMapping("/administrativo") // Mapeia a URL raiz
    public String home() {
        return "admin/painel_administrativo"; // Retorna a página painel_administrativo.html
    }
}
