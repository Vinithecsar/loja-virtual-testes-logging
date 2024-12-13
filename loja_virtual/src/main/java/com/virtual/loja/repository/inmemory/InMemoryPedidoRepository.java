package com.virtual.loja.repository.inmemory;

import com.virtual.loja.model.Pedido;
import com.virtual.loja.repository.PedidoRepository;
import java.util.ArrayList;
import java.util.List;

public class InMemoryPedidoRepository implements PedidoRepository {
  private final List<Pedido> pedidos;

  public InMemoryPedidoRepository() {
    this.pedidos = new ArrayList<>();
  }

  @Override
  public boolean salvarPedido(Pedido pedido) throws IllegalArgumentException {
    boolean clienteJaExiste = pedidos.stream()
        .anyMatch(p -> p.getCliente().equalsIgnoreCase(pedido.getCliente()));

    if (clienteJaExiste) {
      throw new IllegalArgumentException("Já existe um pedido associado ao cliente: " + pedido.getCliente());
    }

    return pedidos.add(pedido);
  }

  @Override
  public Pedido buscarPedidoPorCliente(String cliente) throws IllegalArgumentException {
    return pedidos.stream()
        .filter(pedido -> pedido.getCliente().equals(cliente))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Pedido do cliente " + cliente + " não encontrado"));
  }

  @Override
  public List<Pedido> listarPedidos() {
    return new ArrayList<>(pedidos);
  }
}
