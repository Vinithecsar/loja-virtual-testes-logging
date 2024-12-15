package com.virtual.loja.integration.service;

import static org.junit.jupiter.api.Assertions.*;

import com.virtual.loja.model.Pedido;
import com.virtual.loja.model.Produto;
import com.virtual.loja.repository.inmemory.InMemoryPedidoRepository;
import com.virtual.loja.service.PedidoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class PedidoServiceIntegrationTest {

  private PedidoService pedidoService;

  @BeforeEach
  void setUp() {
    InMemoryPedidoRepository repository = new InMemoryPedidoRepository();
    pedidoService = new PedidoService(repository);
  }

  @Test
  void criarPedido_comSucesso() {
    String cliente = "Cliente 1";

    boolean resultado = pedidoService.criarPedido(cliente);

    assertTrue(resultado, "O pedido deveria ser criado com sucesso");
    Optional<Pedido> pedido = pedidoService.buscarPedidoPorCliente(cliente);
    assertTrue(pedido.isPresent(), "O pedido deveria existir para o cliente");
    assertEquals(cliente, pedido.get().getCliente(), "O nome do cliente no pedido não corresponde");
  }

  @Test
  void criarPedido_clienteDuplicadoDeveLancarExcecao() {
    String cliente = "Cliente 1";
    pedidoService.criarPedido(cliente);

    boolean resultado = pedidoService.criarPedido(cliente);

    assertFalse(resultado, "Não deveria ser possível criar um pedido duplicado para o mesmo cliente");
  }

  @Test
  void adicionarProdutosAoPedido_comSucesso() {
    String cliente = "Cliente 1";
    pedidoService.criarPedido(cliente);

    Produto produto1 = new Produto("Produto 1", 10f);
    Produto produto2 = new Produto("Produto 2", 20f);

    boolean adicionadoProduto1 = pedidoService.adicionarProdutoAoPedido(cliente, produto1);
    boolean adicionadoProduto2 = pedidoService.adicionarProdutoAoPedido(cliente, produto2);

    assertTrue(adicionadoProduto1, "O produto 1 deveria ser adicionado com sucesso");
    assertTrue(adicionadoProduto2, "O produto 2 deveria ser adicionado com sucesso");

    Optional<Pedido> pedido = pedidoService.buscarPedidoPorCliente(cliente);
    assertTrue(pedido.isPresent(), "O pedido deveria existir para o cliente");
    assertEquals(2, pedido.get().getProdutos().size(), "O pedido deveria conter dois produtos");
  }

  @Test
  void calcularTotal_doPedidoSemDesconto() {
    String cliente = "Cliente 1";
    pedidoService.criarPedido(cliente);

    Produto produto1 = new Produto("Produto 1", 30f);
    Produto produto2 = new Produto("Produto 2", 40f);
    pedidoService.adicionarProdutoAoPedido(cliente, produto1);
    pedidoService.adicionarProdutoAoPedido(cliente, produto2);

    float total = pedidoService.calcularTotal(cliente);

    assertEquals(70f, total, "O valor total do pedido deveria ser 70.0");
  }

  @Test
  void calcularTotal_doPedidoComDesconto() {
    String cliente = "Cliente 1";
    pedidoService.criarPedido(cliente);

    Produto produto1 = new Produto("Produto 1", 60f);
    Produto produto2 = new Produto("Produto 2", 50f);
    pedidoService.adicionarProdutoAoPedido(cliente, produto1);
    pedidoService.adicionarProdutoAoPedido(cliente, produto2);

    float total = pedidoService.calcularTotal(cliente);

    assertEquals(99f, total, "O valor total com desconto deveria ser 99.0");
  }

  @Test
  void listarPedidos_comSucesso() {
    pedidoService.criarPedido("Cliente 1");
    pedidoService.criarPedido("Cliente 2");

    List<Pedido> pedidos = pedidoService.listarPedidos();

    assertEquals(2, pedidos.size(), "Deveria haver dois pedidos listados");
  }
}
