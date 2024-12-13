package com.virtual.loja.model;

import java.util.ArrayList;
import java.util.List;

public class Catalogo {
  private List<Produto> produtos;

  public Catalogo() {
    this.produtos = new ArrayList<>();
  }

  public boolean adicionarProduto(Produto produto) throws IllegalArgumentException {
    boolean pedidoJaExisteNoCatalogo = produtos.stream()
        .anyMatch(p -> p.getId() == produto.getId());

    if (pedidoJaExisteNoCatalogo) {
      throw new IllegalArgumentException("O produto de ID " + produto.getId() + " já está no catálogo");
    }

    return produtos.add(produto);

  }

  public List<Produto> listarProdutos() {
    return new ArrayList<>(produtos);
  }

  public Produto buscarProdutoPorId(int id) throws IllegalArgumentException {
    return produtos.stream()
        .filter(produto -> produto.getId() == id)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Produto de ID-" + id + " não encontrado"));
  }
}
