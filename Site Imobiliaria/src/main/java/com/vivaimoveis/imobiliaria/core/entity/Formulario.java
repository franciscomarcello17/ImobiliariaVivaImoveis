package com.vivaimoveis.imobiliaria.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Formulario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String assunto;
    @Enumerated(EnumType.STRING)
    private Urgencia urgencia;
    @Lob
    private String mensagem;
    private String anexo;
    @Enumerated(EnumType.STRING)
    private Situacao situacao = Situacao.ATIVO;

    private LocalDateTime dataCriacao = LocalDateTime.now();

    public enum Urgencia {
        LOW, MEDIUM, HIGH
    }

    public enum Situacao {
        ATIVO, INATIVO
    }
}
