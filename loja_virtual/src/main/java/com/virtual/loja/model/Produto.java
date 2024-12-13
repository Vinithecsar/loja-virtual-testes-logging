package com.virtual.loja.model;

import com.virtual.loja.utils.UniqueIdGenerator;

import lombok.Data;

@Data
public class Produto {
  private int id;
  private String nome;
  private float preco;

  Produto(String nome, float preco) {
    this.id = UniqueIdGenerator.generateId();
    this.nome = nome;
    this.preco = preco;
  }

}
