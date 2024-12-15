package com.virtual.loja.integration.service;

import static org.junit.jupiter.api.Assertions.*;

import com.virtual.loja.model.Produto;
import com.virtual.loja.repository.inmemory.InMemoryCatalogoRepository;
import com.virtual.loja.service.CatalogoService;
import com.virtual.loja.utils.UniqueIdGenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class CatalogoServiceIntegrationTest {

  private CatalogoService catalogoService;

  @BeforeEach
  void setUp() {
    UniqueIdGenerator.reset();
    InMemoryCatalogoRepository repository = new InMemoryCatalogoRepository();
    catalogoService = new CatalogoService(repository);
  }

  @Test
  void adicionarProduto_deveAdicionarProdutoComSucesso() {
    Produto produto = new Produto("Produto 1", 10f);

    boolean resultado = catalogoService.adicionarProduto(produto);
    assertTrue(resultado, "O produto deveria ser adicionado com sucesso");

    List<Produto> produtos = catalogoService.listarProdutos();
    assertEquals(1, produtos.size(), "Deveria haver um produto no catálogo");
    assertEquals("Produto 1", produtos.get(0).getNome(), "O nome do produto não corresponde");
  }

  @Test
  void adicionarProduto_deveLancarExcecaoParaProdutoDuplicado() {
    Produto produto = new Produto("Produto 1", 10f);
    catalogoService.adicionarProduto(produto);

    Produto produtoDuplicado = new Produto("Produto 1", 10f);
    produtoDuplicado.setId(produto.getId());

    boolean resultado = catalogoService.adicionarProduto(produtoDuplicado);
    assertFalse(resultado, "Não deveria ser possível adicionar um produto duplicado");
  }

  @Test
  void buscaProdutoPorId_deveRetornarProdutoCorreto() {
    Produto produto = new Produto("Produto 1", 10f);
    catalogoService.adicionarProduto(produto);

    Optional<Produto> produtoBuscado = catalogoService.buscaProdutoPorId(produto.getId());

    assertTrue(produtoBuscado.isPresent(), "O produto deveria ser encontrado");
    assertEquals(produto.getNome(), produtoBuscado.get().getNome(), "O nome do produto buscado não corresponde");
  }

  @Test
  void buscaProdutoPorId_deveRetornarEmptyParaProdutoNaoExistente() {
    Optional<Produto> produtoBuscado = catalogoService.buscaProdutoPorId(0);

    assertFalse(produtoBuscado.isPresent(), "Não deveria haver produto com ID inexistente");
  }

  @Test
  void listarProdutos_deveRetornarTodosProdutos() {
    Produto produto1 = new Produto("Produto 1", 10f);
    Produto produto2 = new Produto("Produto 2", 20f);

    catalogoService.adicionarProduto(produto1);
    catalogoService.adicionarProduto(produto2);

    List<Produto> produtos = catalogoService.listarProdutos();

    assertEquals(2, produtos.size(), "A quantidade de produtos no catálogo está incorreta");
    assertTrue(produtos.stream().anyMatch(p -> p.getNome().equals("Produto 1")), "Produto 1 deveria estar na lista");
    assertTrue(produtos.stream().anyMatch(p -> p.getNome().equals("Produto 2")), "Produto 2 deveria estar na lista");
  }
}
