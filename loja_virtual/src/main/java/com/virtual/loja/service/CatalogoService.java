package com.virtual.loja.service;

import java.util.List;

import com.virtual.loja.model.Produto;
import com.virtual.loja.repository.CatalogoRepository;

public class CatalogoService {
  private final CatalogoRepository catalogoRepository;

  public CatalogoService(CatalogoRepository catalogoRepository) {
    this.catalogoRepository = catalogoRepository;
  }

  public void adicionarProduto(Produto produto) {
    this.catalogoRepository.adicionarProduto(produto);
  }

  public List<Produto> listarProdutos() {
    return catalogoRepository.listarProdutos();
  }

  public Produto buscaProdutoPorId(int id) {
    return catalogoRepository.buscarProdutoPorId(id);
  }
}
