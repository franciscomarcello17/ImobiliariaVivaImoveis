package com.vivaimoveis.imobiliaria.core.repository;

import com.vivaimoveis.imobiliaria.core.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Métodos personalizados podem ser adicionados aqui se necessário
}
