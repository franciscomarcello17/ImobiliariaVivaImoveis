package com.vivaimoveis.imobiliaria.core.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Visita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data;

    @ManyToOne
    private Cliente cliente;  // Cliente que agendou a visita

    @ManyToOne
    private Imovel imovel;  // Imóvel a ser visitado

    // Construtores
    public Visita() {}

    public Visita(LocalDate data, Cliente cliente, Imovel imovel) {
        this.data = data;
        this.cliente = cliente;
        this.imovel = imovel;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Imovel getImovel() {
        return imovel;
    }

    public void setImovel(Imovel imovel) {
        this.imovel = imovel;
    }
}
