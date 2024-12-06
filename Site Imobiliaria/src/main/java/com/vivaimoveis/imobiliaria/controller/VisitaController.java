package com.vivaimoveis.imobiliaria.controller;

import com.vivaimoveis.imobiliaria.core.entity.Visita;
import com.vivaimoveis.imobiliaria.core.repository.ImovelRepository;
import com.vivaimoveis.imobiliaria.core.repository.ClienteRepository;
import com.vivaimoveis.imobiliaria.core.repository.VisitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/visitas")
public class VisitaController {

    @Autowired
    private VisitaRepository visitaRepository;
    @Autowired
    private ImovelRepository imovelRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    // Exibe o formulário para agendar uma visita
    @GetMapping("/novo")
    public String novaVisitaForm(Model model) {
        model.addAttribute("visita", new Visita());
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("imoveis", imovelRepository.findAll());
        return "visitas/form";  // Retorna o template form.html
    }

    // Processa o envio do formulário para agendar uma nova visita
    @PostMapping
    public String salvarVisita(@ModelAttribute Visita visita) {
        visita.setData(LocalDate.now());  // Define a data da visita para a data atual
        visitaRepository.save(visita);  // Salva a visita no banco de dados
        return "redirect:/visitas";  // Redireciona para a lista de visitas
    }

    // Lista todas as visitas agendadas
    @GetMapping
    public String listarVisitas(Model model) {
        model.addAttribute("visitas", visitaRepository.findAll());
        return "visitas/lista";  // Retorna o template lista.html
    }
}
