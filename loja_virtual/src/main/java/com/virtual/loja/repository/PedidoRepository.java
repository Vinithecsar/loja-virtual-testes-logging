package com.virtual.loja.repository;

import com.virtual.loja.model.Produto;

public interface PedidoRepository {

  void adicionarProduto(Produto produto);

  float calcularTotal();

  String getCliente();

  void setCliente(String cliente);
}
