package com.vendas.vendas.controller;

import com.vendas.vendas.Service.PedidoService;
import com.vendas.vendas.dto.AtualizacaoStatusPedidoDTO;
import com.vendas.vendas.dto.InformacoesItemPedidoDTO;
import com.vendas.vendas.dto.InformacoesPedidoDTO;
import com.vendas.vendas.dto.PedidoDTO;
import com.vendas.vendas.entity.ItemPedido;
import com.vendas.vendas.entity.Pedido;
import com.vendas.vendas.enums.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody @Valid PedidoDTO dto){
        return service.salvar(dto).getId();

    }

    @GetMapping("/{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id){
        return service
                .obterPedidoCompleto(id)
                .map(pedido -> converterPedido(pedido))
                .orElseThrow(()->
                        new ResponseStatusException(NOT_FOUND, "Pedido não encontrado"));

    }

    @PatchMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto){
        String novoStatus = dto.getNovoStatus();
        service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
    }

    private InformacoesPedidoDTO converterPedido(Pedido pedido){
        List<InformacoesItemPedidoDTO> infomacaoPedido = converterItemPedido(pedido.getItens());
        return new InformacoesPedidoDTO(
                pedido.getId(),
                pedido.getCliente().getCpf(),
                pedido.getCliente().getNome(),
                pedido.getTotal(),
                pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                pedido.getStatus().name(),
                infomacaoPedido);

    }

    private List<InformacoesItemPedidoDTO> converterItemPedido(List<ItemPedido> itens){
        if(CollectionUtils.isEmpty(itens)){
            return new ArrayList<>();
        }

        return itens.stream()
                .map(item -> {
                    InformacoesItemPedidoDTO informacoesItemPedido = new InformacoesItemPedidoDTO();
                    informacoesItemPedido.setQuantidade(item.getQuantidade());
                    informacoesItemPedido.setPrecoUnitario(item.getProduto().getPreco());
                    informacoesItemPedido.setDescricaoProduto(item.getProduto().getDescricao());
                    return informacoesItemPedido;
                }).collect(Collectors.toList());
    }


}
