package com.virtual.loja.unit.service;

import com.virtual.loja.model.Produto;
import com.virtual.loja.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoServiceTest {

  private ProdutoService produtoService;

  @BeforeEach
  void setUp() {
    produtoService = new ProdutoService();
  }

  @Test
  void deveCriarProduto() {
    Produto produto = produtoService.criarProduto("Produto 1", 25f);

    assertNotNull(produto, "O produto criado não deveria ser nulo");
    assertEquals("Produto 1", produto.getNome(), "O nome do produto não corresponde ao esperado");
    assertEquals(25f, produto.getPreco(), "O preço do produto não corresponde ao esperado");
    assertTrue(produto.getId() > 0, "O ID do produto deveria ser maior que zero");
  }
}
