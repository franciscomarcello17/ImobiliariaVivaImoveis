package com.vivaimoveis.imobiliaria.core.repository;

import com.vivaimoveis.imobiliaria.core.entity.Formulario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormularioRepository extends JpaRepository<Formulario, Long> {

    // Metodo para encontrar formulários por ID
    Optional<Formulario> findById(Long id);

    // Metodo para encontrar formulários por outros parâmetros
    List<Formulario> findByNomeContainingAndEmailContainingAndAssuntoContaining(
            String nome, String email, Long id
    );

    // Metodo para combinar filtros (ID, nome, e-mail, etc.)
    @Query("SELECT f FROM Formulario f WHERE "
            + "(:id IS NULL OR f.id = :id) "
            + "AND (:nome IS NULL OR f.nome LIKE %:nome%) "
            + "AND (:email IS NULL OR f.email LIKE %:email%) "
            + "AND (:assunto IS NULL OR f.assunto LIKE %:assunto%)")
    List<Formulario> findByFilters(
            @Param("id") Long id,
            @Param("nome") String nome,
            @Param("email") String email,
            @Param("assunto") String assunto
    );
}
