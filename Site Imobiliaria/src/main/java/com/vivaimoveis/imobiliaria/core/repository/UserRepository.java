package com.vivaimoveis.imobiliaria.core.repository;

import com.vivaimoveis.imobiliaria.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndActiveTrue(String username);
}
