package com.virtual.loja.unit.service;

import com.virtual.loja.model.Pedido;
import com.virtual.loja.model.Produto;
import com.virtual.loja.repository.PedidoRepository;
import com.virtual.loja.service.PedidoService;
import com.virtual.loja.utils.UniqueIdGenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

  private PedidoRepository mockPedidoRepository;
  private PedidoService pedidoService;

  @BeforeEach
  void setUp() {
    UniqueIdGenerator.reset();
    mockPedidoRepository = Mockito.mock(PedidoRepository.class);
    pedidoService = new PedidoService(mockPedidoRepository);
  }

  @Test
  void deveCriarPedidoComSucesso() {
    String cliente = "Cliente 1";
    Pedido pedido = new Pedido(cliente);

    when(mockPedidoRepository.salvarPedido(pedido)).thenReturn(true);

    boolean resultado = pedidoService.criarPedido(cliente);

    assertTrue(resultado, "O pedido deveria ter sido criado com sucesso");
    verify(mockPedidoRepository, times(1)).salvarPedido(any(Pedido.class));
  }

  @Test
  void naoDeveCriarPedidoComFalha() {
    String cliente = "Cliente 1";
    Pedido pedido = new Pedido(cliente);

    when(mockPedidoRepository.salvarPedido(pedido)).thenReturn(false);

    boolean resultado = pedidoService.criarPedido(cliente);

    assertFalse(resultado, "O pedido não deveria ter sido criado com sucesso");
    verify(mockPedidoRepository, times(1)).salvarPedido(any(Pedido.class));
  }

  @Test
  void naoDeveCriarPedidoClienteJaExistente() {
    String cliente = "Cliente 1";
    Pedido pedido = new Pedido(cliente);

    when(mockPedidoRepository.salvarPedido(pedido)).thenThrow(new IllegalArgumentException("Cliente já possui pedido"));

    boolean resultado = pedidoService.criarPedido(cliente);

    assertFalse(resultado, "O pedido não deveria ser criado se o cliente já possui um pedido");
    verify(mockPedidoRepository, times(1)).salvarPedido(any(Pedido.class));
  }

  @Test
  void deveAdicionarProdutoAoPedidoComSucesso() {
    String cliente = "Cliente 1";
    Produto produto = new Produto("Produto 1", 10f);
    Pedido pedido = new Pedido(cliente);

    when(mockPedidoRepository.buscarPedidoPorCliente(cliente)).thenReturn(pedido);

    boolean resultado = pedidoService.adicionarProdutoAoPedido(cliente, produto);

    assertTrue(resultado, "O produto deveria ser adicionado ao pedido com sucesso");
    verify(mockPedidoRepository, times(1)).buscarPedidoPorCliente(cliente);
  }

  @Test
  void deveAdicionarProdutoAoPedidoComFalha() {
    String cliente = "Cliente 1";
    Produto produto = new Produto("Produto 1", 10f);
    Pedido pedido = mock(Pedido.class);

    when(mockPedidoRepository.buscarPedidoPorCliente(cliente)).thenReturn(pedido);
    when(pedido.adicionarProduto(produto)).thenReturn(false);

    boolean resultado = pedidoService.adicionarProdutoAoPedido(cliente, produto);

    assertFalse(resultado, "O produto não deveria ser adicionado ao pedido com sucesso");
    verify(mockPedidoRepository, times(1)).buscarPedidoPorCliente(cliente);
  }

  @Test
  void naoDeveAdicionarProdutoAoPedidoClienteNaoExiste() {
    String cliente = "Cliente 1";
    Produto produto = new Produto("Produto 1", 10f);

    when(mockPedidoRepository.buscarPedidoPorCliente(cliente))
        .thenThrow(new IllegalArgumentException("Pedido não encontrado"));

    boolean resultado = pedidoService.adicionarProdutoAoPedido(cliente, produto);

    assertFalse(resultado, "O produto não deveria ser adicionado se o cliente não possui um pedido");
    verify(mockPedidoRepository, times(1)).buscarPedidoPorCliente(cliente);
  }

  @Test
  void deveCalcularTotalSemDesconto() {
    String cliente = "Cliente 1";
    Pedido pedido = new Pedido(cliente);
    pedido.adicionarProduto(new Produto("Produto 1", 50f));
    pedido.adicionarProduto(new Produto("Produto 2", 40f));

    when(mockPedidoRepository.buscarPedidoPorCliente(cliente)).thenReturn(pedido);

    float total = pedidoService.calcularTotal(cliente);

    assertEquals(90f, total, "O total do pedido deveria ser calculado corretamente sem desconto");
    verify(mockPedidoRepository, times(1)).buscarPedidoPorCliente(cliente);
  }

  @Test
  void deveCalcularTotalComDesconto() {
    String cliente = "Cliente 1";
    Pedido pedido = new Pedido(cliente);
    pedido.adicionarProduto(new Produto("Produto 1", 70f));
    pedido.adicionarProduto(new Produto("Produto 2", 50f));

    when(mockPedidoRepository.buscarPedidoPorCliente(cliente)).thenReturn(pedido);

    float total = pedidoService.calcularTotal(cliente);

    assertEquals(108f, total, "O total do pedido deveria ser calculado corretamente com desconto aplicado");
    verify(mockPedidoRepository, times(1)).buscarPedidoPorCliente(cliente);
  }

  @Test
  void deveListarPedidos() {
    Pedido pedido1 = new Pedido("Cliente 1");
    Pedido pedido2 = new Pedido("Cliente 2");

    when(mockPedidoRepository.listarPedidos()).thenReturn(Arrays.asList(pedido1, pedido2));

    List<Pedido> pedidos = pedidoService.listarPedidos();

    assertEquals(2, pedidos.size(), "A quantidade de pedidos retornada está incorreta");
    assertTrue(pedidos.contains(pedido1), "Pedido 1 deveria estar na lista");
    assertTrue(pedidos.contains(pedido2), "Pedido 2 deveria estar na lista");
    verify(mockPedidoRepository, times(1)).listarPedidos();
  }

  @Test
  void deveBuscarPedidoPorClienteComSucesso() {
    String cliente = "Cliente 1";
    Pedido pedido = new Pedido(cliente);

    when(mockPedidoRepository.buscarPedidoPorCliente(cliente)).thenReturn(pedido);

    Optional<Pedido> resultado = pedidoService.buscarPedidoPorCliente(cliente);

    assertTrue(resultado.isPresent(), "O pedido deveria estar presente");
    assertEquals(pedido, resultado.get(), "O pedido retornado não corresponde ao esperado");
    verify(mockPedidoRepository, times(1)).buscarPedidoPorCliente(cliente);
  }

  @Test
  void naoDevePermitirBuscarPedidoPorClienteNaoEncontrado() {
    String cliente = "Cliente 1";

    when(mockPedidoRepository.buscarPedidoPorCliente(cliente))
        .thenThrow(new IllegalArgumentException("Pedido não encontrado"));

    Optional<Pedido> resultado = pedidoService.buscarPedidoPorCliente(cliente);

    assertFalse(resultado.isPresent(), "O pedido não deveria estar presente se o cliente não foi encontrado");
    verify(mockPedidoRepository, times(1)).buscarPedidoPorCliente(cliente);
  }
}
