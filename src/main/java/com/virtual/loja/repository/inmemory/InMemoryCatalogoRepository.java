package com.virtual.loja.repository.inmemory;

import java.util.List;

import com.virtual.loja.model.Catalogo;
import com.virtual.loja.model.Produto;
import com.virtual.loja.repository.CatalogoRepository;

public class InMemoryCatalogoRepository implements CatalogoRepository {
  private final Catalogo catalogo;

  public InMemoryCatalogoRepository() {
    this.catalogo = new Catalogo();
  }

  @Override
  public boolean adicionarProduto(Produto produto) throws IllegalArgumentException {
    return catalogo.adicionarProduto(produto);
  }

  @Override
  public Produto buscarProdutoPorId(int id) throws IllegalArgumentException {
    return catalogo.buscarProdutoPorId(id);
  }

  @Override
  public List<Produto> listarProdutos() {
    return catalogo.listarProdutos();
  }
}
