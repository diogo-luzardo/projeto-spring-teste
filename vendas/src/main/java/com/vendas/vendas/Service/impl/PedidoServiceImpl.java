package com.vendas.vendas.Service.impl;

import com.vendas.vendas.Service.PedidoService;
import com.vendas.vendas.dto.ItemPedidoDTO;
import com.vendas.vendas.dto.PedidoDTO;
import com.vendas.vendas.entity.Cliente;
import com.vendas.vendas.entity.ItemPedido;
import com.vendas.vendas.entity.Pedido;
import com.vendas.vendas.entity.Produto;
import com.vendas.vendas.enums.StatusPedido;
import com.vendas.vendas.excepetion.PedidoNaoEncontradoException;
import com.vendas.vendas.excepetion.RegraNegocioException;
import com.vendas.vendas.jpaRepository.Clientes;
import com.vendas.vendas.jpaRepository.ItemsPedido;
import com.vendas.vendas.jpaRepository.Pedidos;
import com.vendas.vendas.jpaRepository.Produtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private Pedidos pedidoRepository;

    @Autowired
    private Clientes clienteRepository;

    @Autowired
    private Produtos produtoRepository;

    @Autowired
    private ItemsPedido itemsPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO pedidoDTO) {
        Integer idCliente = pedidoDTO.getCliente();
        Cliente cliente = clienteRepository
                .findById(idCliente)
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido"));

        Pedido pedido = new Pedido();
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItemPedido> itemPedidos = conveterItemsParaPedido(pedido, pedidoDTO.getItems());
        pedidoRepository.save(pedido);
        itemsPedidoRepository.saveAll(itemPedidos);
        pedido.setItens(itemPedidos);
        return pedido;
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {
        return pedidoRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setStatus(statusPedido);
                    return pedidoRepository.save(pedido);
                })
                .orElseThrow(PedidoNaoEncontradoException::new);

    }

    private List<ItemPedido> conveterItemsParaPedido(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items");
        }

        return items
                .stream()
                .map(item -> {
                    Integer idProduto = item.getProduto();
                    Produto produto = produtoRepository
                            .findById(idProduto)
                            .orElseThrow(() -> new RegraNegocioException("Código de produto inválido: " + idProduto));

                    ItemPedido itemPedido = new ItemPedido();
                    itemPedido.setQuantidade(item.getQuantidade());
                    itemPedido.setPedido(pedido);
                    itemPedido.setProduto(produto);
                    return itemPedido;

                }).collect(Collectors.toList());
    }
}
