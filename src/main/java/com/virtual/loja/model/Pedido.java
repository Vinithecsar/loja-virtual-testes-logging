package com.virtual.loja.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Pedido {
  private String cliente;
  private List<Produto> produtos;

  public Pedido(String cliente) {
    this.cliente = cliente;
    this.produtos = new ArrayList<>();
  }

  public boolean adicionarProduto(Produto produto) {
    return this.produtos.add(produto);
  }

  public float calcularTotal() {
    float valorTotal = 0;

    for (Produto p : produtos) {
      valorTotal += p.getPreco();
    }

    return valorTotal;
  }

}
