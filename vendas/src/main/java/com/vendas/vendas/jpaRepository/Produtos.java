package com.vendas.vendas.jpaRepository;

import com.vendas.vendas.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Produtos extends JpaRepository<Produto, Integer> {

}
