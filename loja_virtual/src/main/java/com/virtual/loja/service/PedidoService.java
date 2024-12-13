package com.virtual.loja.service;

import com.virtual.loja.model.Produto;
import com.virtual.loja.repository.PedidoRepository;

public class PedidoService {
  private final PedidoRepository pedidoRepository;

  public PedidoService(PedidoRepository pedidoRepository) {
    this.pedidoRepository = pedidoRepository;
  }

  public void adicionarProduto(Produto produto) {
    pedidoRepository.adicionarProduto(produto);
  }

  public float calcularTotal() {
    return pedidoRepository.calcularTotal();
  }

  public void setCliente(String nomeCliente) {
    pedidoRepository.setCliente(nomeCliente);
  }

  public String getCliente() {
    return pedidoRepository.getCliente();
  }
}
