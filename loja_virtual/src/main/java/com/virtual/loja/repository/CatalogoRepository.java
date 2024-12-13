package com.virtual.loja.repository;

import java.util.List;

import com.virtual.loja.model.Produto;

public interface CatalogoRepository {
  void adicionarProduto(Produto produto);

  List<Produto> listarProdutos();

  Produto buscarProdutoPorId(int id);
}
