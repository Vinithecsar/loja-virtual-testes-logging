package com.virtual.loja.unit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.virtual.loja.model.Pedido;
import com.virtual.loja.model.Produto;
import com.virtual.loja.utils.UniqueIdGenerator;

class PedidoTest {

  private Pedido pedido;

  @BeforeEach
  void setUp() {
    UniqueIdGenerator.reset();
    pedido = new Pedido("Cliente 1");
  }

  @Test
  void deveAdicionarProduto() {
    Produto produto = new Produto("Produto 1", 10f);

    boolean resultado = pedido.adicionarProduto(produto);

    assertTrue(resultado, "Produto não foi adicionado ao pedido");
    assertEquals(1, pedido.getProdutos().size(), "A quantidade de produtos no pedido está incorreta");
    assertEquals("Produto 1", pedido.getProdutos().get(0).getNome(), "O nome do produto não corresponde");
  }

  @Test
  void deveCalcularTotalPedidoVazio() {
    float total = pedido.calcularTotal();
    assertEquals(0f, total, "O valor total do pedido vazio deveria ser 0");
  }

  @Test
  void deveCalcularTotalComUmProduto() {
    Produto produto = new Produto("Produto 1", 10f);

    pedido.adicionarProduto(produto);

    float total = pedido.calcularTotal();
    assertEquals(10f, total, "O valor total do pedido está incorreto");
  }

  @Test
  void deveCalcularTotalComDoisProdutos() {
    Produto produto1 = new Produto("Produto 1", 10f);
    Produto produto2 = new Produto("Produto 2", 20f);

    pedido.adicionarProduto(produto1);
    pedido.adicionarProduto(produto2);

    float total = pedido.calcularTotal();
    assertEquals(30f, total, "O valor total do pedido está incorreto");
  }

}
