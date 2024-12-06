package com.vivaimoveis.imobiliaria.core.repository;

import com.vivaimoveis.imobiliaria.core.entity.Visita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitaRepository extends JpaRepository<Visita, Long> {
    // Métodos personalizados podem ser adicionados aqui se necessário
}
