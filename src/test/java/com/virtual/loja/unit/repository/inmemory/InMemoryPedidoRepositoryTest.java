package com.virtual.loja.unit.repository.inmemory;

import com.virtual.loja.model.Pedido;
import com.virtual.loja.repository.inmemory.InMemoryPedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryPedidoRepositoryTest {

  private InMemoryPedidoRepository repository;

  @BeforeEach
  void setUp() {
    repository = new InMemoryPedidoRepository();
  }

  @Test
  void deveSalvarPedido() {
    Pedido pedido = new Pedido("Cliente 1");

    boolean resultado = repository.salvarPedido(pedido);

    assertTrue(resultado, "O pedido não foi salvo no repositório");
    assertEquals(1, repository.listarPedidos().size(), "A quantidade de pedidos no repositório está incorreta");
  }

  @Test
  void naoDevepermitirSalvarPedidoClienteDuplicado() {
    Pedido pedido1 = new Pedido("Cliente 1");
    Pedido pedido2 = new Pedido("Cliente 1");

    repository.salvarPedido(pedido1);

    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      repository.salvarPedido(pedido2);
    });

    assertEquals("Já existe um pedido associado ao cliente: Cliente 1", exception.getMessage(),
        "A mensagem da exceção está incorreta");
  }

  @Test
  void deveBuscarPedidoPorCliente() {
    Pedido pedido = new Pedido("Cliente 1");

    repository.salvarPedido(pedido);

    Pedido pedidoBuscado = repository.buscarPedidoPorCliente("Cliente 1");

    assertNotNull(pedidoBuscado, "O pedido não foi encontrado no repositório");
    assertEquals("Cliente 1", pedidoBuscado.getCliente(), "O cliente do pedido não corresponde");
  }

  @Test
  void naoDevePermitirBuscarPedidoPorClienteInexistente() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
      repository.buscarPedidoPorCliente("Cliente Inexistente");
    });

    assertEquals("Pedido do cliente Cliente Inexistente não encontrado", exception.getMessage(),
        "A mensagem da exceção está incorreta");
  }

  @Test
  void deveListarPedidos() {
    Pedido pedido1 = new Pedido("Cliente 1");
    Pedido pedido2 = new Pedido("Cliente 2");

    repository.salvarPedido(pedido1);
    repository.salvarPedido(pedido2);

    List<Pedido> pedidos = repository.listarPedidos();

    assertEquals(2, pedidos.size(), "A quantidade de pedidos listados está incorreta");
    assertTrue(pedidos.stream().anyMatch(p -> p.getCliente().equals("Cliente 1")),
        "Pedido do Cliente 1 não foi encontrado");
    assertTrue(pedidos.stream().anyMatch(p -> p.getCliente().equals("Cliente 2")),
        "Pedido do Cliente 2 não foi encontrado");
  }
}
