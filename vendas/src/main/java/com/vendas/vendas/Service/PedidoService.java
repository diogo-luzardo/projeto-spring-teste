package com.vendas.vendas.Service;

import com.vendas.vendas.dto.PedidoDTO;
import com.vendas.vendas.entity.Pedido;
import com.vendas.vendas.enums.StatusPedido;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);

    Optional<Pedido> obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);
}
