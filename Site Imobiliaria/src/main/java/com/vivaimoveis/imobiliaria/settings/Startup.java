package com.vivaimoveis.imobiliaria.settings;

import com.vivaimoveis.imobiliaria.core.entity.User;
import com.vivaimoveis.imobiliaria.core.entity.UserRole;
import com.vivaimoveis.imobiliaria.core.repository.UserRepository;
import com.vivaimoveis.imobiliaria.core.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Startup {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (userRoleRepository.findAll().isEmpty()) {
            UserRole userRole = new UserRole();
            userRole.setCode("ROLE_ADMIN");
            userRole.setName("Administrador de Sistema");
            userRoleRepository.save(userRole);

            userRole = new UserRole();
            userRole.setCode("ROLE_USER");
            userRole.setName("Usuário");
            userRoleRepository.save(userRole);

            userRole = new UserRole();
            userRole.setCode("ROLE_MANAGER");
            userRole.setName("Gerente");
            userRoleRepository.save(userRole);
        }

        if (userRepository.findAll().isEmpty()) {
            var roleAdmin = userRoleRepository.findAll()
                    .stream()
                    .filter(userRole -> userRole.getCode().equals("ROLE_ADMIN"))
                    .findFirst();

            User user = new User();
            user.setUsername("admin");
            user.setActive(true);
            user.setFullName("Admin Account");
            user.setBirthDate(LocalDate.of(2000, 1, 1));
            user.setRole(roleAdmin.get());
            user.setPassword(bCryptPasswordEncoder.encode("senha123"));
            userRepository.save(user);

            var roleUser = userRoleRepository.findAll()
                    .stream()
                    .filter(userRole -> userRole.getCode().equals("ROLE_USER"))
                    .findFirst();

            user = new User();
            user.setUsername("user");
            user.setActive(true);
            user.setFullName("User Account");
            user.setBirthDate(LocalDate.of(2000, 1, 1));
            user.setRole(roleUser.get());
            user.setPassword(bCryptPasswordEncoder.encode("senha123"));
            userRepository.save(user);

            var roleManager = userRoleRepository.findAll()
                    .stream()
                    .filter(userRole -> userRole.getCode().equals("ROLE_MANAGER"))
                    .findFirst();

            user = new User();
            user.setUsername("manager");
            user.setActive(true);
            user.setFullName("Manager Account");
            user.setBirthDate(LocalDate.of(2000, 1, 1));
            user.setRole(roleManager.get());
            user.setPassword(bCryptPasswordEncoder.encode("senha123"));
            userRepository.save(user);
        }
    }
}
