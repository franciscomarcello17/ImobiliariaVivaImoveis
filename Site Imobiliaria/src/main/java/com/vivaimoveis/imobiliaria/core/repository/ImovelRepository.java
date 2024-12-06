package com.vivaimoveis.imobiliaria.core.repository;

import com.vivaimoveis.imobiliaria.core.entity.Imovel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImovelRepository extends JpaRepository<Imovel, Long> {
    // Métodos personalizados podem ser adicionados aqui se necessário

    List<Imovel> findByTipo(String tipo);
}
