package com.vivaimoveis.imobiliaria.core.service;

import com.vivaimoveis.imobiliaria.core.entity.Imovel;
import com.vivaimoveis.imobiliaria.core.repository.ImovelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImovelService {

    @Autowired
    private ImovelRepository imovelRepository;

    public Imovel findById(Long id) {
        return imovelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Imóvel não encontrado com ID: " + id));
    }
}
