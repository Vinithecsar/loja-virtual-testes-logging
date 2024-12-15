package com.virtual.loja.unit.service;

import com.virtual.loja.model.Produto;
import com.virtual.loja.repository.CatalogoRepository;
import com.virtual.loja.service.CatalogoService;
import com.virtual.loja.utils.UniqueIdGenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CatalogoServiceTest {

  private CatalogoRepository mockCatalogoRepository;
  private CatalogoService catalogoService;

  @BeforeEach
  void setUp() {
    UniqueIdGenerator.reset();
    mockCatalogoRepository = Mockito.mock(CatalogoRepository.class);
    catalogoService = new CatalogoService(mockCatalogoRepository);
  }

  @Test
  void deveAdicionarProdutoComSucesso() {
    Produto produto = new Produto("Produto 1", 10f);

    when(mockCatalogoRepository.adicionarProduto(produto)).thenReturn(true);

    boolean resultado = catalogoService.adicionarProduto(produto);

    assertTrue(resultado, "O produto deveria ter sido adicionado com sucesso");
    verify(mockCatalogoRepository, times(1)).adicionarProduto(produto);
  }

  @Test
  void deveAdicionarProdutoComFalha() {
    Produto produto = new Produto("Produto 1", 10f);

    when(mockCatalogoRepository.adicionarProduto(produto)).thenReturn(false);

    boolean resultado = catalogoService.adicionarProduto(produto);

    assertFalse(resultado, "O produto não deveria ter sido adicionado com sucesso");
    verify(mockCatalogoRepository, times(1)).adicionarProduto(produto);
  }

  @Test
  void naoDevePermitirAdicionarProdutoJaExistente() {
    Produto produto = new Produto("Produto 1", 10f);

    when(mockCatalogoRepository.adicionarProduto(produto)).thenThrow(new IllegalArgumentException("Produto já existe"));

    boolean resultado = catalogoService.adicionarProduto(produto);

    assertFalse(resultado, "O produto não deveria ser adicionado se já existir no catálogo");
    verify(mockCatalogoRepository, times(1)).adicionarProduto(produto);
  }

  @Test
  void deveBuscaProdutoPorIdComSucesso() {
    Produto produto = new Produto("Produto 1", 10f);

    when(mockCatalogoRepository.buscarProdutoPorId(produto.getId())).thenReturn(produto);

    Optional<Produto> resultado = catalogoService.buscaProdutoPorId(produto.getId());

    assertTrue(resultado.isPresent(), "O produto deveria estar presente");
    assertEquals(produto, resultado.get(), "O produto retornado não corresponde");
    verify(mockCatalogoRepository, times(1)).buscarProdutoPorId(produto.getId());
  }

  @Test
  void naoDevePermitirBuscaProdutoPorIdInexistente() {
    when(mockCatalogoRepository.buscarProdutoPorId(1))
        .thenThrow(new IllegalArgumentException("Produto não encontrado"));

    Optional<Produto> resultado = catalogoService.buscaProdutoPorId(1);

    assertFalse(resultado.isPresent(), "O produto não deveria estar presente");
    verify(mockCatalogoRepository, times(1)).buscarProdutoPorId(1);
  }

  @Test
  void deveListarProdutos() {
    Produto produto1 = new Produto("Produto 1", 10f);
    Produto produto2 = new Produto("Produto 2", 20f);

    when(mockCatalogoRepository.listarProdutos()).thenReturn(Arrays.asList(produto1, produto2));

    List<Produto> produtos = catalogoService.listarProdutos();

    assertEquals(2, produtos.size(), "A quantidade de produtos retornada está incorreta");
    assertTrue(produtos.contains(produto1), "Produto 1 deveria estar na lista");
    assertTrue(produtos.contains(produto2), "Produto 2 deveria estar na lista");
    verify(mockCatalogoRepository, times(1)).listarProdutos();
  }
}
