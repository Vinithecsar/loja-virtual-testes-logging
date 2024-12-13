package com.virtual.loja.repository;

import com.virtual.loja.model.Pedido;
import java.util.List;

public interface PedidoRepository {
  boolean salvarPedido(Pedido pedido) throws IllegalArgumentException;

  Pedido buscarPedidoPorCliente(String cliente) throws IllegalArgumentException;

  List<Pedido> listarPedidos();
}
