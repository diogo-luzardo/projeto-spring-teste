package com.vendas.vendas.dto;

import java.math.BigDecimal;
import java.util.List;

public class InformacoesPedidoDTO {

    private Integer codigo;
    private String cpf;
    private String nomeCliente;
    private BigDecimal total;
    private String dataPedido;
    private String status;
    private List<InformacoesItemPedidoDTO> itensPedido;

    public InformacoesPedidoDTO() {
    }

    public InformacoesPedidoDTO(Integer codigo, String cpf, String nomeCliente, BigDecimal total, String dataPedido, String status, List<InformacoesItemPedidoDTO> itensPedido) {
        this.codigo = codigo;
        this.cpf = cpf;
        this.nomeCliente = nomeCliente;
        this.total = total;
        this.dataPedido = dataPedido;
        this.status = status;
        this.itensPedido = itensPedido;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<InformacoesItemPedidoDTO> getItensPedido() {
        return itensPedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setItensPedido(List<InformacoesItemPedidoDTO> itensPedido) {
        this.itensPedido = itensPedido;
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(String dataPedido) {
        this.dataPedido = dataPedido;
    }

    @Override
    public String toString() {
        return "InformacoesPedidoDTO{" +
                "codigo=" + codigo +
                ", cpf='" + cpf + '\'' +
                ", nomeCliente='" + nomeCliente + '\'' +
                ", total=" + total +
                ", itensPedido=" + itensPedido +
                '}';
    }
}
