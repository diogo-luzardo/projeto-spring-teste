package com.vendas.vendas.dto;

import java.math.BigDecimal;
import java.util.List;

public class ItemPedidoDTO {

    private Integer produto;
    private Integer quantidade;

    public Integer getProduto() {
        return produto;
    }

    public void setProduto(Integer produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
