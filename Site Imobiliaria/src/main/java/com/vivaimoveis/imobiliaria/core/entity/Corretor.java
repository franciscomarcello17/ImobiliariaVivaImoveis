package com.vivaimoveis.imobiliaria.core.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Corretor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String creci;  // Registro do corretor

    @OneToMany(mappedBy = "corretor")
    private List<Imovel> imoveis;  // Relacionamento com os imóveis

    // Construtores
    public Corretor() {}

    public Corretor(String nome, String creci) {
        this.nome = nome;
        this.creci = creci;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCreci() {
        return creci;
    }

    public void setCreci(String creci) {
        this.creci = creci;
    }

    public List<Imovel> getImoveis() {
        return imoveis;
    }

    public void setImoveis(List<Imovel> imoveis) {
        this.imoveis = imoveis;
    }
}
