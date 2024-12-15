package com.virtual.loja.unit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.virtual.loja.model.Catalogo;
import com.virtual.loja.model.Produto;
import com.virtual.loja.utils.UniqueIdGenerator;

class CatalogoTest {

  private Catalogo catalogo;

  @BeforeEach
  void setUp() {
    UniqueIdGenerator.reset();
    catalogo = new Catalogo();
  }

  @Test
  void deveAdicionarProduto() {
    Produto produto = new Produto("Produto 1", 10f);

    boolean resultado = catalogo.adicionarProduto(produto);

    Produto produtoNoCatalogo = catalogo.listarProdutos().get(0);

    assertTrue(resultado, "Produto não foi adicionado ao catálogo");
    assertEquals(1, catalogo.listarProdutos().size(), "A quantidade de produtos no catálogo está incorreta");
    assertEquals("Produto 1", produtoNoCatalogo.getNome(), "O nome do produto não corresponde");
  }

  @Test
  void naoDevePermitirAdicionarProdutoComIdDuplicado() {
    Produto produto = new Produto("Produto 1", 10f);

    catalogo.adicionarProduto(produto);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> {
          catalogo.adicionarProduto(produto);
        });

    assertEquals("O produto de ID 1 já está no catálogo", exception.getMessage());

  }

  @Test
  void deveListarProdutos() {
    Produto produto1 = new Produto("Produto 1", 10f);
    Produto produto2 = new Produto("Produto 2", 20f);

    catalogo.adicionarProduto(produto1);
    catalogo.adicionarProduto(produto2);

    assertEquals(2, catalogo.listarProdutos().size(), "O número de produtos listados está incorreto");
    assertTrue(catalogo.listarProdutos().stream()
        .anyMatch(produto -> produto.getNome().equals("Produto 1")),
        "Produto 1 não encontrado na lista");
    assertTrue(catalogo.listarProdutos().stream()
        .anyMatch(produto -> produto.getNome().equals("Produto 2")),
        "Produto 2 não encontrado na lista");
  }

  @Test
  void deveBuscarProdutoPorId() {
    Produto produto1 = new Produto("Produto 1", 10f);
    Produto produto2 = new Produto("Produto 2", 20f);

    catalogo.adicionarProduto(produto1);
    catalogo.adicionarProduto(produto2);

    Produto produtoBuscado = catalogo.buscarProdutoPorId(1);
    assertNotNull(produtoBuscado, "Produto não encontrado");
    assertEquals("Produto 1", produtoBuscado.getNome(), "O nome do produto não corresponde");

    Produto produtoBuscado2 = catalogo.buscarProdutoPorId(2);
    assertNotNull(produtoBuscado2, "Produto não encontrado");
    assertEquals("Produto 2", produtoBuscado2.getNome(), "O nome do produto não corresponde");
  }

  @Test
  void naoDevePermitirBuscarProdutoPorIdInexistente() {
    Produto produto1 = new Produto("Produto 1", 10f);
    Produto produto2 = new Produto("Produto 2", 20f);

    catalogo.adicionarProduto(produto1);
    catalogo.adicionarProduto(produto2);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      catalogo.buscarProdutoPorId(3);
    });

    assertEquals("Produto de ID-3 não encontrado", exception.getMessage());
  }
}
