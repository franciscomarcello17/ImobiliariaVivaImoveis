package com.vivaimoveis.imobiliaria.core.service;

import com.vivaimoveis.imobiliaria.core.entity.User;
import com.vivaimoveis.imobiliaria.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    private final String UPLOAD_DIR = "uploads/"; // Diretório para armazenar imagens

    private static final Logger logger = LoggerFactory.getLogger(UserService.class); // Logger para a classe

    @Autowired
    private UserRepository userRepository; // Usando o repositório UserRepository

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void registerUser(User user, MultipartFile profilePicture) throws IllegalArgumentException {
        logger.info("Iniciando o registro de usuário: {}", user.getUsername());

        // Verifica se o usuário tem mais de 18 anos
        if (!user.isAdult()) {
            logger.error("Usuário menor de 18 anos: {}", user.getUsername());
            throw new IllegalArgumentException("Usuários menores de 18 anos não podem se cadastrar.");
        }

        // Se houver uma foto de perfil, salvamos ela
        if (!profilePicture.isEmpty()) {
            try {
                logger.info("Processando foto de perfil para o usuário: {}", user.getUsername());

                String fileName = UUID.randomUUID().toString() + "_" + profilePicture.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR, fileName);

                // Cria o diretório se necessário
                Files.createDirectories(filePath.getParent());
                profilePicture.transferTo(filePath); // Salva o arquivo de imagem

                // A URL da imagem é salva no banco de dados
                user.setAvatarUrl(filePath.toString());
                logger.info("Foto de perfil salva em: {}", filePath);

            } catch (IOException e) {
                logger.error("Erro ao salvar a foto de perfil para o usuário: {}", user.getUsername(), e);
            }
        }

        // Criptografa a senha antes de salvar
        logger.info("Criptografando a senha do usuário: {}", user.getUsername());
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setActive(true);  // Garante que o usuário será registrado como ativo

        try {
            // Salvando o usuário no banco de dados
            userRepository.save(user);
            logger.info("Usuário registrado com sucesso: {}", user.getUsername());
        } catch (Exception e) {
            logger.error("Erro ao salvar o usuário: {}", user.getUsername(), e);
        }
    }

    public String getFullnameByUsername(String username) {
        logger.info("Buscando o nome completo para o usuário: {}", username);

        // Busca o usuário no banco de dados
        User user = userRepository.findByUsernameAndActiveTrue(username)
                .orElse(null);  // Retorna null se não encontrar o usuário

        if (user != null) {
            logger.info("Usuário encontrado: {}", user.getUsername());
        } else {
            logger.warn("Usuário não encontrado para o username: {}", username);
        }

        // Retorna o nome completo (fullname) se o usuário for encontrado
        return user != null ? user.getFullName() : null;
    }
}
