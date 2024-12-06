package com.vivaimoveis.imobiliaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.vivaimoveis.imobiliaria.core.service.UserService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalModelAttributes {

    private static final String AVATAR_PATH = "/images/avatars/";

    @Autowired
    private UserService userService; // Serviço para recuperar o fullname

    @ModelAttribute
    public void addAuthenticationToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Adiciona a autenticação ao modelo global
        model.addAttribute("authentication", authentication);

        // Adiciona as autoridades ao modelo como uma lista de strings
        List<String> authorities = authentication.getAuthorities().stream()
                .map(auth -> auth.getAuthority()) // Extrai o nome das roles (por exemplo: 'ROLE_USER')
                .collect(Collectors.toList());
        model.addAttribute("authorities", authorities);

        // Verifica se o usuário está autenticado
        if (authentication != null && !authentication.getName().equals("anonymousUser")) {
            String username = authentication.getName();

            // Busca o fullname do usuário com base no nome de login (username)
            String fullname = userService.getFullnameByUsername(username);

            // Adiciona o fullname ao modelo global
            model.addAttribute("fullname", fullname);

            // Verifica se o arquivo do avatar existe
            Path avatarPath = Paths.get(AVATAR_PATH + username + ".jpg");
            boolean avatarExists = Files.exists(avatarPath);

            // Adiciona o status do avatar ao modelo global
            model.addAttribute("avatarExists", avatarExists);
        }

        // Para depuração (opcional, pode remover depois de testar)
        System.out.println("Authentication: " + authentication);
        System.out.println("Fullname: " + (authentication != null ? userService.getFullnameByUsername(authentication.getName()) : "null"));
        System.out.println("========================================================================");
    }
}
