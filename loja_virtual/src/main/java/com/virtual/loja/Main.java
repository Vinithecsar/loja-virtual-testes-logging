package com.virtual.loja;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.virtual.loja.model.Produto;
import com.virtual.loja.repository.CatalogoRepository;
import com.virtual.loja.repository.PedidoRepository;
import com.virtual.loja.repository.inmemory.InMemoryCatalogoRepository;
import com.virtual.loja.repository.inmemory.InMemoryPedidoRepository;
import com.virtual.loja.service.CatalogoService;
import com.virtual.loja.service.PedidoService;
import com.virtual.loja.service.ProdutoService;

public class Main {
    protected static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        // logger.trace("Trace - Entering method processOrder().");
        // logger.debug("Debug - Received order with ID 12345.");
        // logger.info("Info - Order shipped successfully.");
        // logger.warn("Warn - Potential security vulnerability detected in user input:
        // '...'");
        // logger.error("Error - Failed to process order. Error: {. . .}");
        // logger.fatal("Fatal - System crashed. Shutting down...");

        CatalogoRepository catalogoRepository = new InMemoryCatalogoRepository();
        PedidoRepository pedidoRepository = new InMemoryPedidoRepository();

        CatalogoService catalogoService = new CatalogoService(catalogoRepository);
        PedidoService pedidoService = new PedidoService(pedidoRepository);
        ProdutoService produtoService = new ProdutoService();

        Produto produto1 = produtoService.criarProduto("Produto A", 50f);
        Produto produto2 = produtoService.criarProduto("Produto B", 75f);

        // Métodos de catalogoService
        catalogoRepository.adicionarProduto(produto1);
        catalogoRepository.adicionarProduto(produto2);

        catalogoService.listarProdutos().forEach(produto -> System.out
                .println("ID-" + produto.getId() + " Produto: " + produto.getNome() + " - R$" + produto.getPreco()));

        Optional<Produto> produtoEncontrado = catalogoService.buscaProdutoPorId(produto1.getId());
        if (produtoEncontrado.isPresent()) {
            Produto produto = produtoEncontrado.get();
            System.out.println("Produto encontrado: " + produto.getNome() + " - R$" + produto.getPreco());
        } else {
            System.out.println("Produto com ID " + produto1.getId() + " não encontrado.");
        }

        Optional<Produto> produtoNaoEncontrado = catalogoService.buscaProdutoPorId(0);
        if (produtoNaoEncontrado.isPresent()) {
            Produto produto = produtoNaoEncontrado.get();
            System.out.println("Produto encontrado: " + produto.getNome() + " - R$" + produto.getPreco());
        } else {
            System.out.println("Produto com ID " + 0 + " não encontrado.");
        }

        // Métodos de pedidoService
        String cliente1 = "João";
        boolean pedidoCriado = pedidoService.criarPedido(cliente1);
        if (pedidoCriado) {
            System.out.println("Pedido criado para o cliente: " + cliente1);
        } else {
            System.out.println("Falha ao criar pedido para o cliente: " + cliente1);
        }

        pedidoService.adicionarProdutoAoPedido(cliente1, produto1);
        pedidoService.adicionarProdutoAoPedido(cliente1, produto2);

        float totalPedido = pedidoService.calcularTotal(cliente1);
        System.out.println("Total do pedido para o cliente " + cliente1 + ": R$" + totalPedido);

        pedidoService.buscarPedidoPorCliente(cliente1)
                .ifPresentOrElse(
                        pedido -> System.out.println("Pedido encontrado para o cliente " + cliente1),
                        () -> System.out.println("Pedido não encontrado para o cliente " + cliente1));

        pedidoService.buscarPedidoPorCliente("Maria")
                .ifPresentOrElse(
                        pedido -> System.out.println("Pedido encontrado para o cliente " + "Maria"),
                        () -> System.out.println("Pedido não encontrado para o cliente " + "Maria"));

        pedidoService.criarPedido("Pedro");
        pedidoService.listarPedidos().forEach(pedido -> System.out
                .println("Cliente: " + pedido.getCliente() + ", Produtos: " + pedido.getProdutos().size()));
    }
}