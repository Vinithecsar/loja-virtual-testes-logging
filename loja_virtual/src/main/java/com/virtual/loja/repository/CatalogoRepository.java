package com.virtual.loja.repository;

import java.util.List;

import com.virtual.loja.model.Produto;

public interface CatalogoRepository {
  boolean adicionarProduto(Produto produto) throws IllegalArgumentException;

  Produto buscarProdutoPorId(int id) throws IllegalArgumentException;

  List<Produto> listarProdutos();
}
