package com.virtual.loja.unit.repository.inmemory;

import com.virtual.loja.model.Produto;
import com.virtual.loja.repository.inmemory.InMemoryCatalogoRepository;
import com.virtual.loja.utils.UniqueIdGenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class InMemoryCatalogoRepositoryTest {

  private InMemoryCatalogoRepository repository;

  @BeforeEach
  void setUp() {
    UniqueIdGenerator.reset();
    repository = new InMemoryCatalogoRepository();
  }

  @Test
  void deveAdicionarProduto() {
    Produto produto = new Produto("Produto 1", 10f);

    boolean resultado = repository.adicionarProduto(produto);
    assertTrue(resultado, "Produto não foi adicionado ao repositório");

    List<Produto> produtos = repository.listarProdutos();
    assertEquals(1, produtos.size(), "A quantidade de produtos no repositório está incorreta");
    assertEquals("Produto 1", produtos.get(0).getNome(), "O nome do produto não corresponde");
  }

  @Test
  void deveBuscarProdutoPorId() {
    Produto produto = new Produto("Produto 1", 10f);
    repository.adicionarProduto(produto);

    Produto produtoBuscado = repository.buscarProdutoPorId(produto.getId());
    assertNotNull(produtoBuscado, "Produto não encontrado no repositório");
    assertEquals("Produto 1", produtoBuscado.getNome(), "O nome do produto não corresponde");
  }

  @Test
  void naoDevePermitirBuscarProdutoPorIdInexistente() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      repository.buscarProdutoPorId(0);
    });

    assertEquals("Produto de ID-0 não encontrado", exception.getMessage(), "Mensagem da exceção está incorreta");
  }

  @Test
  void deveListarProdutos() {
    Produto produto1 = new Produto("Produto 1", 10f);
    Produto produto2 = new Produto("Produto 2", 20f);

    repository.adicionarProduto(produto1);
    repository.adicionarProduto(produto2);

    List<Produto> produtos = repository.listarProdutos();
    assertEquals(2, produtos.size(), "A quantidade de produtos listados está incorreta");
    assertTrue(produtos.stream().anyMatch(p -> p.getNome().equals("Produto 1")), "Produto 1 não está na lista");
    assertTrue(produtos.stream().anyMatch(p -> p.getNome().equals("Produto 2")), "Produto 2 não está na lista");
  }
}
