package com.virtual.loja.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.virtual.loja.model.Produto;
import com.virtual.loja.repository.CatalogoRepository;

public class CatalogoService {
  protected static final Logger logger = LogManager.getLogger();
  private final CatalogoRepository catalogoRepository;

  public CatalogoService(CatalogoRepository catalogoRepository) {
    this.catalogoRepository = catalogoRepository;
    logger.debug("CatalogoService inicializado");
  }

  public boolean adicionarProduto(Produto produto) {
    try {
      boolean resultado = this.catalogoRepository.adicionarProduto(produto);

      if (resultado) {
        logger.info("Produto ID-{} adicionado ao catálogo", produto.getId());
      } else {
        logger.warn("Falha ao tentar adicionar produto ID-{} ao catálogo", produto.getId());
      }

      return resultado;
    } catch (IllegalArgumentException exception) {
      logger.warn("Detalhes: {}", exception.getMessage());
      return false;
    }
  }

  public Optional<Produto> buscaProdutoPorId(int id) {
    try {
      logger.info("Buscando produto de ID-{}", id);
      return Optional.of(catalogoRepository.buscarProdutoPorId(id));
    } catch (IllegalArgumentException exception) {
      logger.warn("Detalhes: {}", exception.getMessage());
      return Optional.empty();
    }
  }

  public List<Produto> listarProdutos() {
    List<Produto> produtos = catalogoRepository.listarProdutos();
    logger.debug("Lista de produtos solicitada. Quantidade: {}", produtos.size());
    return produtos;
  }

}
