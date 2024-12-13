package com.virtual.loja.service;

import com.virtual.loja.model.Pedido;
import com.virtual.loja.model.Produto;
import com.virtual.loja.repository.PedidoRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class PedidoService {
  private static final Logger logger = LogManager.getLogger(PedidoService.class);
  private final PedidoRepository pedidoRepository;

  public PedidoService(PedidoRepository pedidoRepository) {
    this.pedidoRepository = pedidoRepository;
    logger.debug("PedidoService inicializado");
  }

  public boolean criarPedido(String cliente) {
    Pedido pedido = new Pedido(cliente);
    try {
      boolean sucesso = pedidoRepository.salvarPedido(pedido);

      if (sucesso) {
        logger.info("Pedido criado para o cliente: {}", cliente);
      } else {
        logger.warn("Falha ao tentar criar pedido para o cliente: {}", cliente);
      }

      return sucesso;
    } catch (IllegalArgumentException exception) {
      logger.warn("Detalhes: {}", exception.getMessage());
      return false;
    }
  }

  public boolean adicionarProdutoAoPedido(String cliente, Produto produto) {
    try {
      Pedido pedido = pedidoRepository.buscarPedidoPorCliente(cliente);
      boolean sucesso = pedido.adicionarProduto(produto);

      if (sucesso) {
        logger.info("Produto ID-{} adicionado ao pedido do cliente {}", produto.getId(), cliente);
      } else {
        logger.warn("Falha ao tentar adicionar produto ID-{} ao pedido do cliente {}", produto.getId(), cliente);
      }

      return sucesso;
    } catch (IllegalArgumentException exception) {
      logger.warn("Detalhes: {}", exception.getMessage());
      return false;
    }
  }

  public float calcularTotal(String cliente) {
    Pedido pedido = pedidoRepository.buscarPedidoPorCliente(cliente);
    float total = pedido.calcularTotal();

    if (total >= 100f) {
      total *= 0.9f;
      logger.info("Desconto aplicado para o pedido do cliente {}", cliente);
    }

    logger.info("Total calculado para o pedido do cliente {}: R${}", cliente, total);
    return total;
  }

  public List<Pedido> listarPedidos() {
    List<Pedido> pedidos = pedidoRepository.listarPedidos();
    logger.debug("Lista de pedidos solicitada. Quantidade: {}", pedidos.size());
    return pedidos;
  }

  public Optional<Pedido> buscarPedidoPorCliente(String cliente) {
    try {
      logger.info("Buscando pedido do cliente {}", cliente);
      return Optional.of(pedidoRepository.buscarPedidoPorCliente(cliente));
    } catch (IllegalArgumentException exception) {
      logger.warn("Detalhes: {}", exception.getMessage());
      return Optional.empty();
    }
  }
}
