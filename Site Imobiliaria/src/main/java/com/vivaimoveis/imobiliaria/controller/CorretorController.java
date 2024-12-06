package com.vivaimoveis.imobiliaria.controller;

import com.vivaimoveis.imobiliaria.core.entity.Corretor;
import com.vivaimoveis.imobiliaria.core.repository.CorretorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/corretores")
public class CorretorController {

    @Autowired
    private CorretorRepository corretorRepository;

    // Lista todos os corretores
    @GetMapping
    public String listarCorretores(Model model) {
        model.addAttribute("corretores", corretorRepository.findAll());
        return "corretores/lista";  // Retorna o template lista.html
    }

    // Exibe o formulário para criar um novo corretor
    @GetMapping("/novo")
    public String novoCorretorForm(Model model) {
        model.addAttribute("corretor", new Corretor()); // Cria um objeto vazio para o formulário
        return "corretores/form";  // Retorna o template form.html
    }

    // Processa o envio do formulário para salvar um novo corretor
    @PostMapping
    public String salvarCorretor(@ModelAttribute Corretor corretor) {
        corretorRepository.save(corretor);  // Salva o corretor no banco de dados
        return "redirect:/corretores";  // Redireciona para a lista de corretores
    }

    // Exibe o formulário para editar um corretor existente
    @GetMapping("/{id}/editar")
    public String editarCorretorForm(@PathVariable Long id, Model model) {
        Corretor corretor = corretorRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Corretor não encontrado"));
        model.addAttribute("corretor", corretor);  // Carrega o corretor no modelo para o formulário
        return "corretores/form";  // Retorna o template form.html para edição
    }

    // Processa o envio do formulário de edição
    @PostMapping("/{id}/editar")
    public String atualizarCorretor(@PathVariable Long id, @ModelAttribute Corretor corretor) {
        corretor.setId(id);  // Garante que o ID do corretor será mantido
        corretorRepository.save(corretor);  // Atualiza o corretor no banco de dados
        return "redirect:/corretores";  // Redireciona para a lista de corretores
    }

    // Exclui um corretor
    @GetMapping("/{id}/excluir")
    public String excluirCorretor(@PathVariable Long id) {
        corretorRepository.deleteById(id);  // Exclui o corretor do banco de dados
        return "redirect:/corretores";  // Redireciona para a lista de corretores
    }
}
