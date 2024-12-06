package com.vivaimoveis.imobiliaria.controller;

import com.vivaimoveis.imobiliaria.core.entity.Imovel;
import com.vivaimoveis.imobiliaria.core.repository.ImovelRepository;
import com.vivaimoveis.imobiliaria.core.service.ImovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.List;

@Controller
@RequestMapping("/imovel")
public class ImovelController {

    @Autowired
    private ImovelRepository imovelRepository;
    @Autowired
    private ImovelService imovelService;

    @GetMapping("/comprar")
    public String mostrarImoveisVenda(Model model) {
        // Busca apenas imóveis do tipo "venda"
        List<Imovel> imoveisVenda = imovelRepository.findByTipo("venda");

        model.addAttribute("imoveis", imoveisVenda);

        // Retorna a página Thymeleaf chamada imoveis-venda.html
        return "imoveisavenda";
    }

    @GetMapping("/alugar")
    public String mostrarImoveisAluguel(Model model) {
        // Busca apenas imóveis do tipo "aluguel"
        List<Imovel> imoveisAluguel = imovelRepository.findByTipo("aluguel");

        model.addAttribute("imoveis", imoveisAluguel);

        // Retorna a página Thymeleaf chamada imoveis-aluguel.html
        return "imoveisaluguel";
    }



    @GetMapping("/detalhes/{id}")
    public String detalhesImovel(@PathVariable Long id, Model model) {
        Imovel imovel = imovelService.findById(id);

        // Formatar o preço usando DecimalFormat
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String precoFormatado = df.format(imovel.getPreco());

        model.addAttribute("imovel", imovel);
        model.addAttribute("precoFormatado", precoFormatado);  // Passando o preço formatado

        return "imovel/detalhes";
    }


    // Lista todos os imóveis
    @GetMapping
    public String listarImoveis(Model model) {
        model.addAttribute("imoveis", imovelRepository.findAll());
        return "imoveis/lista";  // Retorna o template lista.html
    }

    // Exibe o formulário para criar um novo imóvel
    @GetMapping("/novo")
    public String novoImovelForm(Model model) {
        model.addAttribute("imovel", new Imovel()); // Cria um objeto vazio para o formulário
        return "imoveis/form";  // Retorna o template form.html
    }

    // Processa o envio do formulário para salvar o novo imóvel
    @PostMapping
    public String salvarImovel(@ModelAttribute Imovel imovel) {
        imovelRepository.save(imovel);  // Salva o imóvel no banco de dados
        return "redirect:/imoveis";  // Redireciona para a lista de imóveis
    }

    // Exibe o formulário para editar um imóvel existente
    @GetMapping("/{id}/editar")
    public String editarImovelForm(@PathVariable Long id, Model model) {
        Imovel imovel = imovelRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Imóvel não encontrado"));
        model.addAttribute("imovel", imovel);  // Carrega o imóvel no modelo para o formulário
        return "imoveis/form";  // Retorna o template form.html para edição
    }

    // Processa o envio do formulário de edição
    @PostMapping("/{id}/editar")
    public String atualizarImovel(@PathVariable Long id, @ModelAttribute Imovel imovel) {
        imovel.setId(id);  // Garante que o ID do imóvel será mantido
        imovelRepository.save(imovel);  // Atualiza o imóvel no banco de dados
        return "redirect:/imoveis";  // Redireciona para a lista de imóveis
    }

    // Exclui um imóvel
    @GetMapping("/{id}/excluir")
    public String excluirImovel(@PathVariable Long id) {
        imovelRepository.deleteById(id);  // Exclui o imóvel do banco de dados
        return "redirect:/imoveis";  // Redireciona para a lista de imóveis
    }
}
