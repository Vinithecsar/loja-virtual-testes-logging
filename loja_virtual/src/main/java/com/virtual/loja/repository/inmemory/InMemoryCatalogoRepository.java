package com.virtual.loja.repository.inmemory;

import java.util.ArrayList;
import java.util.List;

import com.virtual.loja.model.Produto;
import com.virtual.loja.repository.CatalogoRepository;

public class InMemoryCatalogoRepository implements CatalogoRepository {
  private final List<Produto> produtos = new ArrayList<>();

  @Override
  public void adicionarProduto(Produto produto) {
    produtos.add(produto);
  }

  @Override
  public List<Produto> listarProdutos() {
    return new ArrayList<>(produtos);
  }

  @Override
  public Produto buscarProdutoPorId(int id) throws IllegalArgumentException {
    return produtos.stream()
        .filter(produto -> produto.getId() == id)
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));
  }
}
