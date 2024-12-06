package com.vivaimoveis.imobiliaria.core.repository;


import com.vivaimoveis.imobiliaria.core.entity.Corretor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorretorRepository extends JpaRepository<Corretor, Long> {
    // Métodos personalizados podem ser adicionados aqui se necessário
}
