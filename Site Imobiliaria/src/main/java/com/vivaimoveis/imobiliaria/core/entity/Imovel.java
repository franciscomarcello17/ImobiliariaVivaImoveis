package com.vivaimoveis.imobiliaria.core.entity;

import jakarta.persistence.*;

@Entity
public class Imovel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String endereco;
    private Integer quartos;
    private Integer banheiros;
    private Double preco;
    private String tipo;  // Ex: "Apartamento", "Casa", etc.
    private String imagem; // Caminho da imagem


    @ManyToOne
    private Corretor corretor;  // Relacionamento com o Corretor

    // Construtores
    public Imovel() {}

    public Imovel(String endereco, String nome, Integer quartos, Integer banheiros, Double preco, String tipo, Corretor corretor, String imagem) {
        this.endereco = endereco;
        this.nome = nome;
        this.quartos = quartos;
        this.banheiros = banheiros;
        this.preco = preco;
        this.tipo = tipo;
        this.corretor = corretor;
        this.imagem = imagem;

    }

    // Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public Double getPreco() {
        return preco;
    }
    public void setPreco(Double preco) {
        this.preco = preco;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public Corretor getCorretor() {
        return corretor;
    }
    public void setCorretor(Corretor corretor) {
        this.corretor = corretor;
    }
    public String getImagem() {
        return imagem;
    }
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Integer getQuartos() {
        return quartos;
    }
    public void setQuartos(Integer quartos) {
        this.quartos = quartos;
    }
    public Integer getBanheiros() {
        return banheiros;
    }
    public void setBanheiros(Integer banheiros) {
        this.banheiros = banheiros;
    }
}
