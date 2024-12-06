package com.vivaimoveis.imobiliaria.controller;

import com.vivaimoveis.imobiliaria.core.dto.AtualizarSituacaoRequest;
import com.vivaimoveis.imobiliaria.core.entity.Formulario.Situacao;
import com.vivaimoveis.imobiliaria.core.entity.Formulario;
import com.vivaimoveis.imobiliaria.core.repository.FormularioRepository;
import com.vivaimoveis.imobiliaria.core.service.FormularioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/contato")
public class FormularioController {

    @Autowired
    private FormularioRepository formularioRepository;

    @Autowired
    private FormularioService formularioService;

    // Caminho para salvar os arquivos
    private static final String UPLOAD_DIR = "uploads/";

    // Exibir a tela de gerenciamento de formulários
    @GetMapping("/admin/formularios")
    public String listarFormularios(Model model,
                                    @RequestParam(name = "id", required = false) Long id,
                                    @RequestParam(name = "nome", required = false) String nome,
                                    @RequestParam(name = "email", required = false) String email) {

        // Verifica se os parâmetros de filtro foram fornecidos
        List<Formulario> formularios;
        if (nome == null && email == null && id == null) {
            // Se não houver filtros, carrega todos os formulários
            formularios = formularioRepository.findAll();
        } else {
            // Caso contrário, filtra os formulários com base nos parâmetros
            formularios = formularioService.filtrarFormularios(nome, email, id);
        }
        if (formularios != null) {
            formularios.forEach(f -> {
                if (f.getSituacao() == null) {
                    f.setSituacao(Situacao.INATIVO);  // Atribui um valor default se necessário
                }
            });
        }
        model.addAttribute("formularios", formularios);
        model.addAttribute("nome", nome);
        model.addAttribute("email", email);
        return "admin/gerenciar_formularios"; // Página Thymeleaf para gerenciar formulários
    }

    // Alterar o estado de um formulário (ATIVO/INATIVO)
    @PostMapping("formulario/{id}/atualizar-situacao")
    public ResponseEntity<?> atualizarSituacao(
            @PathVariable("id") Long id,
            @RequestBody AtualizarSituacaoRequest request) {

        // A lógica para atualizar a situação do formulário
        Formulario formulario = formularioService.findById(id);
        if (formulario != null) {
            // Verifica se a situação é válida
            try {
                Situacao novaSituacao = Situacao.valueOf(request.getSituacao().toUpperCase()); // Convertendo para o valor enum
                formulario.setSituacao(novaSituacao); // Atualiza a situação do formulário
                formularioService.save(formulario);
                return ResponseEntity.ok().build(); // Retorna sucesso
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Situação inválida!"); // Caso a situação seja inválida
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Formulário não encontrado!"); // Caso o formulário não seja encontrado
    }

    // Excluir o formulário
    @PostMapping("/formulario/{id}/excluir")
    @ResponseBody
    public ResponseEntity<String> excluirFormulario(@PathVariable("id") Long id) {
        try {
            formularioService.excluirFormulario(id);
            return ResponseEntity.ok("Formulário excluído com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir o formulário");
        }
    }

    // Enviar um novo formulário
    @PostMapping("/enviar")
    public String enviarFormulario(@ModelAttribute Formulario formulario,
                                   @RequestParam("attachment") MultipartFile attachment) {
        try {
            // Processar o arquivo de anexo, se houver
            if (!attachment.isEmpty()) {
                // Gerar um nome único para o arquivo
                String fileName = System.currentTimeMillis() + "_" + attachment.getOriginalFilename();
                Path path = Paths.get(UPLOAD_DIR + fileName);
                Files.createDirectories(path.getParent());  // Garante que o diretório exista
                Files.write(path, attachment.getBytes());  // Salva o arquivo no diretório

                // Define o nome do arquivo no formulário
                formulario.setAnexo(fileName);
            }

            // Salva o formulário no banco de dados
            formularioRepository.save(formulario);
            return "contato/sucesso"; // Página de sucesso
        } catch (IOException e) {
            e.printStackTrace();
            return "contato/erro"; // Página de erro
        }
    }
}
