package com.virtual.loja.repository.inmemory;

import java.util.ArrayList;
import java.util.List;

import com.virtual.loja.model.Produto;
import com.virtual.loja.repository.PedidoRepository;

public class InMemoryPedidoRepository implements PedidoRepository {
  private final List<Produto> produtos = new ArrayList<>();
  private String cliente;

  @Override
  public void adicionarProduto(Produto produto) {
    produtos.add(produto);
  }

  @Override
  public float calcularTotal() {
    float valorTotal = 0;

    for (Produto p : produtos) {
      valorTotal += p.getPreco();
    }

    if (valorTotal >= 100.00) {
      valorTotal = valorTotal * 0.9f;
    }
    return valorTotal;
  }

  @Override
  public String getCliente() {
    return cliente;
  }

  @Override
  public void setCliente(String cliente) {
    this.cliente = cliente;
  }
}
