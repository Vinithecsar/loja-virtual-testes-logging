package com.virtual.loja.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.virtual.loja.model.Produto;

public class ProdutoService {
  private static final Logger logger = LogManager.getLogger(ProdutoService.class);

  public ProdutoService() {
    logger.debug("ProdutoService inicializado");
  }

  public Produto criarProduto(String nome, float preco) {
    Produto produto = new Produto(nome, preco);
    logger.info("Produto criado: ID-{} - {} - R${}", produto.getId(), produto.getNome(), produto.getPreco());
    return produto;
  }
}