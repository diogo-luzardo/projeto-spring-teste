package com.vendas.vendas.jpaRepository;

import com.vendas.vendas.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Usuarios extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByNome(String nome);
}
