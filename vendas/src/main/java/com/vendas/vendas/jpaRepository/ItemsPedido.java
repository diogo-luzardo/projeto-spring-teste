package com.vendas.vendas.jpaRepository;

import com.vendas.vendas.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsPedido extends JpaRepository<ItemPedido, Integer> {
}
