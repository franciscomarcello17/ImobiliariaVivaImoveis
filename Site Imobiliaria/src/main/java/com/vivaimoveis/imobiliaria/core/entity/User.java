package com.vivaimoveis.imobiliaria.core.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private LocalDate birthDate;
    private String username;
    private String password;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "role_id")  // Nome da coluna no banco que armazena a chave estrangeira
    private UserRole role;

    // Novo campo para o avatar do usuário
    private String avatarUrl;  // Campo para armazenar a URL do avatar

    public boolean isAdult() {
        return Period.between(this.birthDate, LocalDate.now()).getYears() >= 18;
    }
}
