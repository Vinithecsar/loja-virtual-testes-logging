package com.virtual.loja.unit.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.virtual.loja.utils.UniqueIdGenerator;

class UniqueIdGeneratorTest {

  @BeforeEach
  void setUp() {
    UniqueIdGenerator.reset();
  }

  @Test
  void deveGerarIdsUnicos() {
    int id1 = UniqueIdGenerator.generateId();
    int id2 = UniqueIdGenerator.generateId();

    assertNotEquals(id1, id2, "Ids gerados devem ser únicos");
  }

  @Test
  void deveReiniciarCounter() {
    int id1 = UniqueIdGenerator.generateId();
    UniqueIdGenerator.reset();
    int id2 = UniqueIdGenerator.generateId();

    assertEquals(id1, id2, "Ids gerados não devem ser diferentes após o reset");
  }

}
