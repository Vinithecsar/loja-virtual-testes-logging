package com.virtual.loja.utils;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UniqueIdGenerator {
  protected static final Logger logger = LogManager.getLogger();
  private static final AtomicInteger counter = new AtomicInteger();

  public static int generateId() {
    int contador = counter.incrementAndGet();
    logger.debug("Gerado novo ID: {}", contador);
    return contador;
  }

  private UniqueIdGenerator() {
  }
}
